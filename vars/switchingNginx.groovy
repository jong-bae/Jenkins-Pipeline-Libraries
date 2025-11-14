def call(Map cfg) {
    // Nginx 포트 체인지 로직 추가
    sh """
            sudo /bin/sed -i "s/${cfg.runningPort}/${cfg.newPort}/g" ${cfg.nginxConf}
            sudo /usr/bin/systemctl reload nginx
        """

    // 이전 컨테이너 종료
    sh """
            if [ "\$(docker ps -a -q -f name=${cfg.oldDocker})" ]; then
                docker stop ${cfg.oldDocker} || true
                docker rm ${cfg.oldDocker} || true
            fi
        """

    // 불필요한 이미지 정리
    sh "docker image prune -f"
}