def call(Map cfg) {
    def healthCheck = sh(script: """
                        for i in {1..20}; do
                          STATUS=\$(curl -s -o /dev/null -w "%{http_code}" http://localhost:${cfg.newPort}/actuator/health || true)
                          if [ "\$STATUS" = "200" ]; then
                            echo "✅ 신규 컨테이너 정상 구동"
                            exit 0
                          fi
                          echo "Waiting for app to become healthy... (\$i)"
                          sleep 3
                        done
                        exit 1
                    """,
            returnStatus: true)

    // ❌ 실패시 — nginx 스위칭 금지 + 신규 컨테이너 제거
    if (healthCheck != 0) {
        echo "❌ 신규 컨테이너 Health check 실패 — 배포 실패"
        sh "docker rm -f ${cfg.newDocker} || true"
        error("Health check 실패로 파이프라인 중단")  // stage 즉시 종료
    }
}