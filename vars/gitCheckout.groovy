def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    stage('scm') {
    script{
    echo "Checkout Project"
    echo 'Checking out code from "' + config.repoUrl + '" with credentialsId "' + \
        config.credentialsId + '" ...'
    
    checkout([$class: 'GitSCM', branches: [[name: config.branches]], 
    doGenerateSubmoduleConfigurations: false, 
    extensions: [], 
    submoduleCfg: [], 
    userRemoteConfigs: [[credentialsId: config.credentialsId, url: config.repoUrl]]])   
    }
 }
}
