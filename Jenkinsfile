pipeline {
    agent any

    options {
        // Automatically clean the workspace before each build
        // This prevents corrupted .git object errors
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Use the SCM configured in the job (Pipeline from SCM)
                // This respects the branch and repo URL set in Jenkins UI
                checkout scm
            }
        }

        stage('Build Java App') {
            steps {
                sh 'mvn clean package -DskipTests'
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

    post {
        always {
            // Clean workspace after build to avoid corruption on next run
            cleanWs()
        }
    }
}
