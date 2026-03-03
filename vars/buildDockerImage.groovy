def call(Map cfg) {
    sh """docker build -t ${cfg.imageName}:${cfg.tag} -f ${cfg.dockerfile} ${cfg.contextPath}"""
}
