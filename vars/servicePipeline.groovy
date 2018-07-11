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
                        // 编译和发布项目
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
                script{
                    // 启动服务
                }
            }

        }
    }
}