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
                        // 迁出源代码
                        gitCheckout{
                            repoUrl = pipelineParams.repoUrl
                            credentialsId = pipelineParams.credentialsId
                            branches = pipelineParams.branches
                            commit = pipelineParams.commit
                        }
                        // 编译和发布项目
                        dotnetcoreBuild{
                            workspace = pipelineParams.workspace
                        }
                        // 打包成镜像
                        dockerImageBuild{
                            imageName=pipelineParams.imageName
                            tagId=pipelineParams.tagId
                            context=pipelineParams.context
                        }
                        // 将镜像推到远程仓库
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