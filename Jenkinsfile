pipeline {
    options {
        buildDiscarder logRotator(numToKeepStr: '10')
    }
    agent any
    stages {
        stage("Build and Test") {
            agent {
                docker {
                    image "$DOCKER_REGISTRY/oracle/serverjre:8"
                    registryUrl "https://$DOCKER_REGISTRY/"
                    registryCredentialsId 'nexus'
                }
            }
            steps {
                sh './gradlew clean build'
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
                    sh "./gradlew :api:rest:rest-model:upload :api:rest:rest-client:upload :qup-commons:upload :external-clients:aws-storage:upload"
                }
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
        stage("Deploy") {
            when {
                anyOf {
                    branch 'release';
                    branch 'develop'
                }
            }
            steps {
                script {
                    switch (env.BRANCH_NAME) {
                        case "develop":
                            environment = "development"
                            break
                        case "release":
                            environment = "acceptance"
                            break
                    }
                }
                build(
                    job: 'deploy/master',
                    parameters: [
                        string(name: 'environment', value: environment),
                        booleanParam(name: "core", value: true),
                        booleanParam(name: "coreConsumer", value: true),
                        booleanParam(name: "adminGateway", value: true),
                        booleanParam(name: "coreScheduler", value: true)
                    ],
                    propagate: 'true',
                    wait: 'false'
                )
            }
        }
    }
    post {
        always {
            script {
                switch(currentBuild.currentResult) {
                    case "SUCCESS":
                        color = 'good'
                        break
                    case "UNSTABLE":
                        color = 'warning'
                        break
                    case "FAILURE":
                        color = 'danger'
                        break
                    default:
                        color = '#439FE0'
                        break
                }
            }
            slackSend(
                color: "$color",
                message: "Build Finished with ${currentBuild.currentResult} - <${env.BUILD_URL}|${env.JOB_NAME} ${env.BUILD_NUMBER}>",
                channel: '#qup-jenkins'
            )
        }
    }
}
