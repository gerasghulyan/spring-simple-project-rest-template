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
                withCredentials(
                    [
                        usernamePassword(
                            credentialsId: 'nexus',
                            usernameVariable: 'SONATYPE_USERNAME',
                            passwordVariable: 'SONATYPE_PASSWORD'
                        )
                    ]
                ) {
                sh './gradlew clean build'
               }
            }
        }
        stage("Quality Analysis") {
            agent {
                docker {
                    image "openjdk:8"
                }
            }
            steps {
                withCredentials(
                    [
                        string(
                            credentialsId: 'sonar',
                            variable: 'SONAR_TOKEN'
                        ),
                        usernamePassword(
                            credentialsId: 'nexus',
                            usernameVariable: 'SONATYPE_USERNAME',
                            passwordVariable: 'SONATYPE_PASSWORD'
                        )
                    ]
                ) {
                sh './gradlew sonarqube \
                      -Dsonar.projectKey=vntanaplatform2_vntana-core \
                      -Dsonar.organization=vntanaplatform2 \
                      -Dsonar.host.url=https://sonarcloud.io \
                      -Dsonar.login=$SONAR_TOKEN'
               }
            }
        }
        stage("Upload Maven") {
            when {
                anyOf {
                    branch 'master';
                    branch 'release';
                    branch 'develop'
                }
            }
            steps {
                withCredentials(
                    [
                        usernamePassword(
                            credentialsId: 'nexus',
                            usernameVariable: 'SONATYPE_USERNAME',
                            passwordVariable: 'SONATYPE_PASSWORD'
                        )
                    ]
                ) {
                    sh "./gradlew :api:rest:rest-model:upload :api:rest:rest-client:upload"
                }
            }
        }
        stage("Push Docker") {
            when {
                anyOf {
                    branch 'master';
                    branch 'release';
                    branch 'develop'
                }
            }
            steps {
                withCredentials(
                    [
                        usernamePassword(
                            credentialsId: 'nexus',
                            usernameVariable: 'SONATYPE_USERNAME',
                            passwordVariable: 'SONATYPE_PASSWORD'
                        ),
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