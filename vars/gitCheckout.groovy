def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    echo 'Checking out code from "' + config.repoUrl + '" with credentialsId "' + \
        config.credentialsId + '" ...'
    // checkout source from git
        checkout(changelog: true, poll: true, scm: [$class: 'GitSCM', branches: [[name: config.branches]], \
            doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'UserIdentity', email: 'bfyxzls@sina.com', name: 'bfyxzls']], submoduleCfg: [], \
            userRemoteConfigs: [[credentialsId: config.credentialsId, url: config.repoUrl]]])
    
}
