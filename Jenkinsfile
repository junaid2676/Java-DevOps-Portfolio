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
                sh 'javac -d out src/main/java/com/junaid/portfolio/*.java src/main/java/com/junaid/portfolio/*/*.java'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t java-portfolio:latest .'
            }
        }
        stage('Deploy with Ansible') {
            steps {
                sh 'ansible-playbook -i inventory portfolio.yml'
            }
        }
    }
}
EOF
