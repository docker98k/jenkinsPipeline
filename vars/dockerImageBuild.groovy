//打包镜像
def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    if (config.imageName == null || config.imageName == 'null' || config.imageName == '') {
        throw new Exception("image name is missing!")
    }
    if (config.tagId == null || config.tagId == 'null' || config.tagId == '') {
        throw new Exception("tag is missing! imageName")
    }
    if (config.context == null || config.context == 'null' || config.context == '') {
        throw new Exception("context is missing! imageName")
    }

    sh "docker build --pull -t ${config.imageName}:${config.tagId} ${config.context}"
}
