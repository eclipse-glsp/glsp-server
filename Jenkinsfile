pipeline {
    agent any
    tools {
        maven 'apache-maven-latest'
        jdk 'openjdk-jdk21-latest'
    }

    environment {
        EMAIL_TO= "glsp-build@eclipse.org"
    }

    stages {

        stage ('Build: Eclipse-based (P2)') {
            steps {
                timeout(30) {
                    sh 'mvn clean verify -Pp2 -B'
                }
            }
        }

        stage ('Build: Plain Maven (M2)') {
            steps {
                timeout(30) {
                    sh 'mvn clean verify -Pm2 -B -Dcheckstyle.skip -DskipTests'
                }
            }
        }

        stage ('Build: fatjar') {
            steps {
                timeout(30) {
                    sh 'mvn clean verify -Pfatjar -B -Dcheckstyle.skip -DskipTests'
                }
            }
        }

        stage('Checkstyle') {
            steps {
                timeout(30) {
                     sh ' mvn checkstyle:check -Pm2 -B'
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

        stage('Deploy (master only)') {
            when { branch 'master' }
            stages {
                stage('Deploy P2') {
                    steps {
                        // build the p2 repository into target/repository ...
                        sh "mvn clean install -Pp2 -B"
                        // ... then publish it into the nightly composite site. The
                        // script is fully decoupled from Maven: it detects the version
                        // from the build output and owns the rsync up/down cycle.
                        sh "rm -rf ${WORKSPACE}/p2-update-site/server/p2"
                        sshagent ( ['projects-storage.eclipse.org-bot-ssh']) {
                            sh """
                                releng/org.eclipse.glsp.repository/p2-composite.sh add \
                                  --source-repo ${WORKSPACE}/releng/org.eclipse.glsp.repository/target/repository \
                                  --local ${WORKSPACE}/p2-update-site/server/p2 \
                                  --remote genie.glsp@projects-storage.eclipse.org:/home/data/httpd/download.eclipse.org/glsp/server/p2
                            """
                        }
                    }
                }
                stage('Deploy M2') {
                    steps {
                        timeout(30){
                            withCredentials([file(credentialsId: 'secret-subkeys.asc', variable: 'KEYRING')]) {
                                sh 'gpg --batch --import "${KEYRING}"'
                                sh 'for fpr in $(gpg --list-keys --with-colons  | awk -F: \'/fpr:/ {print $10}\' | sort -u); do echo -e "5\ny\n" |  gpg --batch --command-fd 0 --expert --edit-key ${fpr} trust; done'
                            }
                            sh 'mvn clean deploy -Pm2 -Pm2-release -Pfatjar  -B -pl "!tests,!tests/org.eclipse.glsp.server.test,!tests/org.eclipse.glsp.graph.test"'
                        }
                     }
                }
                stage('Trigger Java25 build') {
                    steps {
                        build wait: false, job: 'glsp-server-java25'
                    }
                }
            }
        }
    }

    post{
        success{
             // Record & publish checkstyle issues
            recordIssues  enabledForFailure: true, publishAllIssues: true,
            tool: checkStyle(reportEncoding: 'UTF-8'),
            qualityGates: [[threshold: 1, type: 'TOTAL', unstable: true]]

             // Record & publish test results
            withChecks('Tests') {
                junit 'tests/**/surefire-reports/*.xml'
            }
            // Record maven,java warnings
            recordIssues enabledForFailure: true, skipPublishingChecks:true, tools: [mavenConsole(), java()]   

            script {
                if (env.BRANCH_NAME == 'master') {
                    archiveArtifacts artifacts: 'p2-update-site/**', followSymlinks: false 
                }
            }
        }
        failure {
            script {
                if (env.BRANCH_NAME == 'master') {
                    echo "Build result FAILURE: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: true,
                    from: 'glsp-bot@eclipse.org',
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} (#${BUILD_NUMBER}) FAILURE', to: "${EMAIL_TO}"
                }
            }
        }
        unstable {
            script {
                if (env.BRANCH_NAME == 'master') {
                    echo "Build result UNSTABLE: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: true,
                    from: 'glsp-bot@eclipse.org',
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} (#${BUILD_NUMBER}) UNSTABLE', to: "${EMAIL_TO}"
                }
            }
        }
        fixed {
            script {
                if (env.BRANCH_NAME == 'master') {
                    echo "Build back to normal: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: false,
                    from: 'glsp-bot@eclipse.org',
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} back to normal (#${BUILD_NUMBER})', to: "${EMAIL_TO}"
                }
            }
        } 
    }
}
