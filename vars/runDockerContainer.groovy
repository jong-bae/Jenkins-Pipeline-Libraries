def call(Map cfg) {
    sh """
        docker rm -f ${cfg.newDocker} || true
        docker run -d --name ${cfg.newDocker} -p ${cfg.newPort}:${cfg.appPort} ${cfg.imageName}:${cfg.tag}
    """
}