def call(body) {
    def config = [ : ]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    stage('building') {
                echo 'building...'
                script {
                    sh returnStdout: true, script: "docker run -v ${config.workspace}/:/src/  --workdir=/src/ --user root --tty --rm microsoft/dotnet:2.0.0-sdk dotnet build"
                }   
            }

}