pipeline {
    options {
        buildDiscarder logRotator(numToKeepStr: '10')
    }
    agent {
        label "jenkins-slave"
    }
    environment {
        GIT_COMMIT = """${sh(
            returnStdout: true,
            script: "git rev-parse --short=10 HEAD"
        )}""".trim()
    }
    stages {
        stage("Build and Test") {
            steps {
                container("gcloud") {
                    script {
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
            }
        }
//         stage("Quality Analysis") {
//             steps {
//                 container("gcloud") {
//                     withCredentials(
//                     [
//                         string(
//                             credentialsId: 'sonar',
//                             variable: 'SONAR_TOKEN'
//                         ),
//                         usernamePassword(
//                             credentialsId: 'nexus',
//                             usernameVariable: 'SONATYPE_USERNAME',
//                             passwordVariable: 'SONATYPE_PASSWORD'
//                         )
//                     ]
//                     ) {
//                         sh './gradlew sonarqube -Dsonar.login=$SONAR_TOKEN'
//                     }
//                 }
//             }
//         }
        stage("Upload Maven") {
            steps {
                container("gcloud") {
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
        }
        stage("Push Docker") {
            steps {
                container("gcloud") {
                        sh 'gcloud config set project $PROJECT_NAME >/dev/null 2>&1'
                        sh 'gcloud auth configure-docker --quiet'
                        
                        sh "./gradlew --exclude-task test pushDockerTags --project-prop dockerRegistry=$DOCKER_GCR_REGISTRY --project-prop removeImage"
                }
            }
        }
        stage("DeployDev") {
            when {
                branch "develop"
            }
            steps{
                container("gcloud") {
                    script {
                        git(
                            url: 'https://bitbucket.org/vntanaplatform2/vntana-configs.git',
                            credentialsId: 'bitbucket',
                            branch: "terraform"
                        )
                        sh 'gcloud container clusters get-credentials development-vntana --zone europe-west4 --project $PROJECT_NAME-development'

                        sh 'helm -n development upgrade --install --wait --atomic --timeout=150s core -f development-configs/vntana/core/values.yaml --set image.tag=$GIT_COMMIT development-configs/vntana/core'
                        sh 'helm -n development upgrade --install --wait --atomic --timeout=150s core-consumer -f development-configs/vntana/core-consumer/values.yaml --set image.tag=$GIT_COMMIT development-configs/vntana/core-consumer'
                    }
                }
            }
        }
    }
}