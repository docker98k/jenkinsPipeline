## 项目的持续集成分享
### 源代码管理
* 项目仓库
* 配置仓库
* 发布仓库

### ci/cd相关
* gitlab，管理版本，测试流水线
* jenkins，对项目进行持续集成

### 各模块的关系
```mermaid
graph TD
a(jenkins piple)-->b(项目A源代码仓库)
a-->c(项目A配置仓库)
a-->d(项目A部署脚本仓库)
```

### jenkins pipe集成项目的过程
```mermaid
graph TD
a1(job启动)-->a2(scm拉pipeline部署代码)
a2-->a(scm拉项目代码)
a-->b(build编译项目)
b-->c(test测试项目)
c-->d(build image构建镜像)
d-->e(tag为镜像打版本号)
e-->f(deploy将镜像推送到仓库)
f-->g(service建立服务)
f-->h(sevice更新服务)
```

一般我们都是按着上面部署进行项目构建的，像这个JOB执行过程一般由运维人员负责，所以我们会将pipe的核心代码抽象成一个文件，存储在自己的仓库里，由开发人员自己去维护，而运维人员只负责点一下按钮即可。这也就是分离关注点的意思。