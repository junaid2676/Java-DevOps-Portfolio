pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Java Application') {
            steps {
                sh '''
                    mkdir -p out
                    find src -name "*.java" > sources.txt
                    javac -d out @sources.txt
                '''
            }
        }
        stage('Docker Build & Push') {
            steps {
                sh '''
                    docker build -t junaid2676/portfolio:latest .
                    # docker login ... (if pushing to Docker Hub)
                    # docker push junaid2676/portfolio:latest
                '''
            }
        }
        stage('Deploy with Ansible') {
            steps {
                sh 'ansible-playbook -i inventory Portfolio.yml'   # match exact filename
            }
        }
    }
}
