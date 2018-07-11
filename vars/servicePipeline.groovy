def call(Map pipelineParams) {

    pipeline {
        agent { label 'build-server' }
       
        stages {
            stage('Initialization') {
                steps {
                    script {
                       echo "项目初始化..."
                    }
                }
            }
            stage('CI') {
                steps{
                    script{
                        echo ".net core项目编译，测试，发布，打包镜像，上传到仓库..." 
                        dotnetcoreBuild{
                            workspace = pipelineParams.workspace
                            repoUrl = pipelineParams.repoUrl
                            credentialsId = pipelineParams.credentialsId
                            branches = pipelineParams.branches
                            commit = pipelineParams.commit
                            imageName=pipelineParams.imageName
                            tagId=pipelineParams.tagId
                            context=pipelineParams.context
                        }
                    }
                  }
            }
            stage('Service') {
                steps{
                script{
                    echo "启动服务..."
                 }
                }
            }

        }
    }
}