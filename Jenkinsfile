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
        stage("Push Docker") {
            steps {
                withCredentials(
                    [
                        usernamePassword(
                            credentialsId: 'nexus',
                            usernameVariable: 'DOCKER_REGISTRY_USERNAME',
                            passwordVariable: 'DOCKER_REGISTRY_PASSWORD'
                        )
                    ]
                ) {
                    sh "./gradlew --exclude-task test pushDockerTags --project-prop dockerRegistry=$DOCKER_REGISTRY --project-prop removeImage"
                }
            }
        }
    }
}