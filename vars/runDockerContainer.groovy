def call(Map cfg) {

    def memory = cfg.memory ?: "1g"
    def springProfile = cfg.profile ?: "prod"

    def dockerOpts = []
    dockerOpts << "-d"
    dockerOpts << "--restart unless-stopped"
    dockerOpts << "--memory ${memory}"
    dockerOpts << "--name ${cfg.newDocker}"
    dockerOpts << "-p ${cfg.newPort}:${cfg.appPort}"

    if(cfg.network) {
        dockerOpts << "--network ${cfg.network}"
    }

    dockerOpts << "-e SPRING_PROFILES_ACTIVE=${springProfile}"

    sh """
        docker rm -f ${cfg.newDocker} || true
        docker run ${dockerOpts.join(' ')} ${cfg.imageName}:${cfg.tag}
    """
}
