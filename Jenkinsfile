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
                sh './gradlew sonarqube -Dsonar.login=$SONAR_TOKEN'
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
                    sh "./gradlew :api:rest:rest-model:upload :api:rest:rest-client:upload :helper:rest-test-helper:upload --refresh-dependencies"
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
        stage("DeployDEV") {
            when {
                 expression {
                    return env.BRANCH_NAME == 'develop'
                }
            }
            steps {
                build(
                    job: 'DeployDEV/master',
                    parameters: [
                        booleanParam(name: "core", value: true),
                        booleanParam(name: "coreConsumer", value: true)
                    ],
                    propagate: 'true',
                    wait: 'false'
                )
            }
        }
        stage("DeployACC") {
            when {
                 expression {
                    return env.BRANCH_NAME == 'release'
                }
            }
            steps {
                build(
                    job: 'DeployACC/master',
                    parameters: [
                        booleanParam(name: "core", value: true)
                    ],
                    propagate: 'true',
                    wait: 'false'
                )
            }
        }
    }
}