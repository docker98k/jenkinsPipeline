def call(body) {
    def config = [: ] 
    body.resolveStrategy = Closure.DELEGATE_FIRST 
    body.delegate = config 
    body() 
    stage('scm') {
     echo 'scm Project'
     script{
        gitCheckout{
        repoUrl = config.repoUrl
        credentialsId = config.credentialsId
        branches = config.branches
        commit = config.commit
      }
     }
    }
    stage('building') {
        echo 'Building DotNet Project'
        script {
            sh returnStdout: true,
            script: "docker run -v $workspace/:/src/  --workdir=${config.workspace} --user root --tty --rm microsoft/dotnet:2.0.0-sdk dotnet build"
        }
    }
    stage('image building') {
        echo 'image building'
        script{
          dockerImageBuild{
            mageName=config.imageName
            tagId=config.tagId
            context=config.context
            }
        }
    }

}