pipeline {
    agent any
    tools {
        maven 'apache-maven-latest'
        jdk 'openjdk-jdk11-latest'
    }
    stages {
        

        stage ('Build: Plain Maven (M2)') {
            steps {
                // Not fail build on stage failure => always execute next stage
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    timeout(30) {
                        sh 'mvn clean verify -Pm2 -B -Dcheckstyle.skip -DskipTests'
                    }
                }
            }
        }

        stage('Checkstyle') {
            steps {
                timeout(30) {
                     sh ' mvn checkstyle:check -Pm2 -B'
                }
            }

            post{
                always{
                    // Record & publish checkstyle issues
                    recordIssues  enabledForFailure: true, publishAllIssues: true,
                    tool: checkStyle(reportEncoding: 'UTF-8'),
                    qualityGates: [[threshold: 1, type: 'TOTAL', unstable: true]]
                }
            }
        } 

        stage('Tests') {
            steps {
                timeout(30) {
                    sh 'mvn test -Pm2 -B'
                }
            }
        }

        stage ('Build: Eclipse-based (P2)') {
            steps {
                timeout(30) {
                    sh 'mvn clean verify -Pp2 -B'
                }
            }
        }



        stage ('Deploy (master only)') {
            when { branch 'master' }
            steps {
                parallel(
                    p2: {
                        build job: 'deploy-p2-glsp-server', wait: false
                    },
                    m2: {
                        build job: 'deploy-m2-glsp-server', wait: false
                    }
                )
            }
        }
    }

    post {
        always {
            // Record & publish test results
            withChecks('Tests') {
                junit 'tests/**/surefire-reports/*.xml'
            }
            // Record maven,java warnings
            recordIssues enabledForFailure: true, skipPublishingChecks:true, tools: [mavenConsole(), java()]
        }
    }
}
