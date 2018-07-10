def call(body) {
    def config = [ : ]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
      stage('build') {
script{
    echo "building image "+config.imageName+":"+config.tagId
    if (config.imageName == null ||config.imageName == 'null' || config.imageName == '') {
    	throw new Exception("image name is missing!")
    } 
    if (config.tagId == null ||config.tagId == 'null' || config.tagId == '') {
    	throw new Exception("tag is missing!")
    } 
    if (config.context == null ||config.context == 'null' || config.context == '') {
    	throw new Exception("context is missing!")
    } 

    sh "docker build --pull -t ${config.imageName}:${config.tagId} ${config.context}"
   }
 }
}
