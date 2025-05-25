pipeline {
    agent any

    stages {
        stage('test') {
            steps {
                bat 'mvn clean test'
            }
        }
   }
   post {
    always {
        cucumber buildStatus: 'UNSTABLE',
                 fileIncludePattern: '**/cucumber.json',
                 jsonReportDirectory: 'target'
    }
}
}