//Jenkinsfile (Declarative Pipeline 另外还有Script语法的Pipeline)

pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying'
            }
        }

        stage('Deliver') {
             steps {
                     sh 'chmod 777 ./jenkins/scripts/deliver.sh'
                     sh './jenkins/scripts/deliver.sh'
                   }
         }
    }
}