pipeline {
    agent any
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/junaid2676/Java-DevOps-Portfolio.git'
            }
        }
        stage('Build Java App') {
            steps {
                // Compiles your Java application source files
                sh 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t junaid2676/portfolio:latest .'
            }
        }
        stage('Deploy with Ansible') {
            steps {
                sh 'ansible-playbook -i inventory portfolio.yml'
            }
        }
    }
}
