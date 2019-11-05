pipeline { 
    agent any  
    tools { 
        maven 'apache-maven-3.6.2'
        jdk 'openjdk-jdk11-latest'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn clean verify' 
            }
        }

        stage('DeployMaster') {
            when { branch 'master'}
            steps {
                sh 'echo "TODO deploy artifacts"'
            }
        }


    }
}
