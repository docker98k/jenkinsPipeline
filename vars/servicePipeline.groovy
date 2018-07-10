def call(Map pipelineParams) {

    pipeline {
        agent { label 'build-server' }
       
        stages {
            stage('Initialization') {
                steps {
                    script {
                       echo "项目初始化"
                    }
                }
            }
            stage('CI') {
                steps{
                    script{
                        gitCheckout{
                            repoUrl = pipelineParams.repoUrl
                            credentialsId = pipelineParams.credentialsId
                            branches = pipelineParams.branches
                            commit = pipelineParams.commit
                        }
                        dotnetcoreBuild{
                            workspace = pipelineParams.workspace
                        }
                        dockerImageBuild{
                            imageName=pipelineParams.imageName
                            tagId=pipelineParams.tagId
                            context=pipelineParams.context
                        }
                    }
                }
            }
        }
    }
}