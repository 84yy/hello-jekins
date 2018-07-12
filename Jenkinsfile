pipeline {
    agent {label "${params.config}"}
        environment {
            def deployAuth = '110'
            def devAuth = '110'  //172.16.200.110
            def sitAuth ='112'  //172.16.200.112
        }
    stages {
        stage('git clone'){
            when {
                 expression { return params.isReBuildImage }
            }
            //从仓库克隆要部署的分支
            steps{
               git branch: "${brachVersion}", credentialsId: '085dbb8c-d1c9-46d1-9a91-e5b7ffe42236', url: 'http://172.16.200.102/dev2/cmp.git'

            }
        }

        stage('mvn deploy with push docker hub'){
            when {
                 expression { return params.isReBuildImage }
            }
            tools {
                //工具名称必须在Jenkins 管理Jenkins → 全局工具配置中预配置。
                maven 'M3'
            }
            steps{
                //mvn构建
               sh "mvn clean  deploy -pl ${params.app} -am -Dmaven.test.skip=true"
            }
        }

        stage('pull docker image '){
            when {
                 expression { return !params.isReBuildImage }
            }
            steps {

                script{
                   def split = "${params.app}".split(",")
                  for(item in split){
                      //echo item
                      //echo "$item"
                      echo "start pull image 172.16.200.101/ht/$item:${params.brachVersion}"
                      echo  "$item port in env is: ${env."$item"}"

                       sh "docker pull 172.16.200.101/ht/$item:${params.brachVersion}"

                      echo "finish pull image 172.16.200.101/ht/$item:${params.brachVersion}"
                  }
               }
            }
        }

        stage('dev deploy') {
            when {
                 expression { return params.config=='dev-120' }
            }
            steps {

                script{
                   def split = "${params.app}".split(",")
                  for(item in split){
                      //echo item
                      //echo "$item"
                      echo "start deploy $item...."
                      echo  "$item port in env is: ${env."$item"}"

                      try{
                            sh "docker rm -f $item"
                         }catch(e){
                             echo "warning  $item  not running before"
                         }


                       sh "docker run --name $item  -p ${env."$item"}:${env."$item"} --restart=always --log-opt max-size=10m --log-opt max-file=3  -v /data/logs:/logs -v /opt/openoffice4:/opt/openoffice4 -v /tmp:/tmp -e SPRING_CLOUD_CONFIG_ENABLE=${env.SPRING_CLOUD_CONFIG_ENABLE}  -e SPRING_CLOUD_EUREKA=${env.SPRING_CLOUD_EUREKA}  -e SPRING_CLOUD_CONFIG_NAME=$item,${env.SPRING_CLOUD_CONFIG_NAME}  -e JAVA_EXTEND1=${env.JAVA_EXTEND1}  -e JAVA_EXTEND2=${env.JAVA_EXTEND2}   -e JAVA_EXTEND3=${env.JAVA_EXTEND3} --net=host  -d  172.16.200.101/ht/$item:${params.brachVersion}"

                      echo "finish deploy $item...."
                  }
               }



                //docker.image("172.16.200.101/ht/${params.app}:${params.brachVersion}").run('-p 30401:30401 --name eip-in')
            }
        }

        stage('sit deploy') {
            when {
                 expression { return params.config=='sit-121' }
            }
            steps {
                script{
                   def split = "${params.app}".split(",")
                  for(item in split){
                      //echo item
                      //echo "$item"
                      echo "start deploy $item...."
                      echo  "$item port in env is: ${env."$item"}"

                      try{
                            sh "docker rm -f $item"
                         }catch(e){
                             echo "warning  $item  not running before"
                         }


                       sh "docker run --name $item  -p ${env."$item"}:${env."$item"} --restart=always --log-opt max-size=10m --log-opt max-file=3  -v /data/logs:/logs -e SPRING_CLOUD_CONFIG_ENABLE=${env.SPRING_CLOUD_CONFIG_ENABLE}  -e SPRING_CLOUD_EUREKA=${env.SPRING_CLOUD_EUREKA}  -e SPRING_CLOUD_CONFIG_NAME=${env.SPRING_CLOUD_CONFIG_NAME},$item  -e JAVA_EXTEND1=${env.JAVA_EXTEND1}  -e JAVA_EXTEND2=${env.JAVA_EXTEND2}    -e JAVA_EXTEND3=${env.JAVA_EXTEND3} --net=host  -d  172.16.200.101/ht/$item:${params.brachVersion}"

                      echo "finish deploy $item...."
                  }
               }



                //docker.image("172.16.200.101/ht/${params.app}:${params.brachVersion}").run('-p 30401:30401 --name eip-in')
            }
        }

    }
}