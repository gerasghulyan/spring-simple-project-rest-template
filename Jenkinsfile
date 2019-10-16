pipeline {
    options {
        buildDiscarder logRotator(numToKeepStr: '10')
    }
    agent any
    stages {
        stage("Build and Test") {
            agent {
                docker {
                    image "openjdk:8"
                }
            }
            steps {
                sh './gradlew clean build'
            }
        }
    }
}