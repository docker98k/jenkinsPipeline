def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    echo '迁出项目从仓库 "' + config.repoUrl + '" 授权号 "' +  \
         config.credentialsId + '" ...'

    checkout([$class                           : 'GitSCM', branches: [[name: config.branches]],
              doGenerateSubmoduleConfigurations: false,
              extensions                       : [],
              submoduleCfg                     : [],
              userRemoteConfigs                : [[credentialsId: config.credentialsId, url: config.repoUrl]]])
}
