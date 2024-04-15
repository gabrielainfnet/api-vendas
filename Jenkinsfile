def VERSION
pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'Java 17'
    }
    stages {
        stage('Checkout') {
            steps {
                git(
                    url: "https://github.com/gabrielainfnet/api-vendas",
                    branch: "master"
                )
            }
        }
        stage('Set Version') {
            steps {
                script {
                    VERSION = sh(
                        returnStdout: true,
                        script: "cat pom.xml | grep -o '<version>[^<]*</version>' | head -1 | sed -e 's/<version>//' -e 's/<\\/version>//'"
                    ).trim()
                    echo "Version: ${VERSION}"
                }
            }
        }
        stage('Build') {
            steps {
                sh "mvn -Dmaven.test.skip=true package"
            }

        }
        stage('Build docker Image') {
            steps {
                script {
                    def version_arg="VERSION=${VERSION}"
                    sh "docker build -t gabrielainfnet/api-vendas:${VERSION} --build-arg=${version_arg} ."
                }
            }
        }
        stage('Publish docker Image') {
            steps {
                sh "docker push gabrielainfnet/api-vendas:${VERSION}"
            }
        }
    }
}