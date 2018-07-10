def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

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
                            repoUrl = config.repoUrl
                            credentialsId = config.credentialsId
                            branches = config.branches
                            commit = config.commit
                        }
                    }
                }
            }
        }
    }
}