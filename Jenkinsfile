pipeline { 
    agent any  
    tools { 
        maven 'apache-maven-3.6.2'
        jdk 'openjdk-jdk11-latest'
    }
    stages {

        stage ('Build') {
            steps {
                sh 'mvn clean verify' 
            }
        }

        stage('Deploy') {
            when { branch 'master'}
            steps {
                 withCredentials([file(credentialsId: 'secret-subkeys.asc', variable: 'KEYRING')]) {
                    sh 'gpg --batch --import "${KEYRING}"'
                    sh 'for fpr in $(gpg --list-keys --with-colons  | awk -F: \'/fpr:/ {print $10}\' | sort -u); do echo -e "5\ny\n" |  gpg --batch --command-fd 0 --expert --edit-key ${fpr} trust; done'
                }
                sh 'mvn deploy -Prelease'
            }
        }
    }
}
