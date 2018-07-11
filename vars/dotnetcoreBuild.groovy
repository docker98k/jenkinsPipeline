// dotnet core项目编译
def call(body) {
    def config = [: ] 
    body.resolveStrategy = Closure.DELEGATE_FIRST 
    body.delegate = config 
    body() 
    stage('scm') {
     echo '从源代码仓库下载项目'
     script{
        gitCheckout{
        repoUrl = config.repoUrl
        credentialsId = config.credentialsId
        branches = config.branches
        commit = config.commit
      }
     }
    }
    stage('build project') {
        echo '编译项目'
        script {
            sh returnStdout: true,
            script: "docker run -v $workspace/:/src/  --workdir=${config.workspace} --user root --tty --rm microsoft/dotnet:2.0.0-sdk dotnet build"
        }
    }
    stage('build image') {
        echo '打包镜像'
        script{
          dockerImageBuild{
            mageName=config.imageName
            tagId=config.tagId
            context=config.context
            }
        }
    }

}