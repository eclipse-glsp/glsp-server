pipeline { 
    agent any  
    tools { 
        maven 'apache-maven-latest'
        jdk 'openjdk-jdk11-latest'
    }
    stages {
        stage ('Build: Plain Maven(M2)') {
            steps {
                sh "mvn clean verify -Pm2 --batch-mode package"    
            }
        }

        stage ('Build: Eclipse-based (P2)') {
            steps {
                sh "mvn clean verify -Pp2 --batch-mode package"    
            }
        }

        stage ('Deploy)') {
            when { branch 'master'}
             steps {
                parallel(
                    p2: {
                        build 'deploy-p2-glsp-server'
                    },
                    m2: {
                        build 'deploy-m2-glsp-server'
                    }
                )
             }
        }
    }
}