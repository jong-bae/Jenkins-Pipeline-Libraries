def call(Map cfg) {
    def bluePort = ${cfg.bluePort}
    def greenPort = ${cfg.greenPort}

    def checkRun = sh(script: "docker ps --filter name=${cfg.imageName}-blue -q", returnStdout: true).trim()

    def runningPort = checkRun ? bluePort : greenPort
    def newPort = (runningPort == bluePort) ? greenPort : bluePort

    return [
            runningPort: runningPort,
            newPort: newPort,
            newDocker: (newPort == bluePort) ? "${cfg.imageName}-blue" : "${cfg.imageName}-green",
            oldDocker: (runningPort == bluePort) ? "${cfg.imageName}-blue" : "${cfg.imageName}-green"
    ]
}