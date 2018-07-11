// dotnet core项目编译
def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    sh returnStdout: true,
            script: "docker run -v $workspace/:/src/  --workdir=${config.workspace} --user root --tty --rm microsoft/dotnet:2.0.0-sdk dotnet build"
}