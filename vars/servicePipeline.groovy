// CI、CD入口
def call(Map pipelineParams) {
    pipeline {
        agent { label 'build-server' }

        stages {
            stage('Initialization') {
                steps {
                    script {
                        echo "持续集成与交付开始..."
                    }
                }
            }
            stage('CI') {
                steps {
                    echo ".net core项目编译，测试，发布，打包镜像，上传到仓库..."
                    script {
                        stage('scm') {
                            echo '从源代码仓库下载项目'
                            script {
                                gitCheckout {
                                    repoUrl = pipelineParams.repoUrl
                                    credentialsId = pipelineParams.credentialsId
                                    branches = pipelineParams.branches
                                    commit = pipelineParams.commit
                                }
                            }
                        }
                        stage('build project') {
                            script {
                                dotnetcoreBuild {
                                    workspace = pipelineParams.workspace
                                }
                            }
                        }
                        stage('build image') {
                            echo '打包镜像'
                            script {
                                dockerImageBuild {
                                    mageName = pipelineParams.imageName
                                    tagId = pipelineParams.tagId
                                    context = pipelineParams.context
                                }
                            }
                        }

                    }
                }
            }
            stage('Deploy Service') {
                steps {
                    script {
                        echo "启动服务..."
                    }
                }
            }

        }
    }
}