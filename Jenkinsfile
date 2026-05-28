pipeline {
    agent any
    tools {
        maven 'Maven-3.9' // Tên bạn vừa đặt ở trên
    }
    environment {
        DOCKERHUB_USER = 'taibaton'
        IMAGE_NAME = "${DOCKERHUB_USER}/ltw"
        // Sử dụng Jenkins Credentials để bảo mật
        DOCKER_CREDS = 'docker' 
        GIT_CREDS = 'f78957c5-d4aa-4ff1-bc1e-2f9de9c0d8b9'
    }

    stages {
        stage('Checkout Code') { // Thêm stage này
            steps {
                // Thay URL bằng repo của bạn
                git branch: 'main', url: 'https://github.com/asbass/qlbh.git'
            }
        }
        stage('Build & Push') {
            steps {
                script {
                    sh 'pwd'
                    // Dùng lệnh ls -F để xem có thấy pom.xml không
                    sh 'ls -F'
                    // 1. Build Maven (Jenkins node cần cài maven)
                    sh 'mvn clean package -DskipTests'
                    
                    // 2. Build Docker
                    docker.withRegistry('', DOCKER_CREDS) {
                        def customImage = docker.build("${IMAGE_NAME}:${BUILD_NUMBER}")
                        customImage.push()
                        customImage.push('latest')
                    }
                }
            }
        }

        stage('Update K8s Repo (GitOps)') {
            steps {
                sshagent([GIT_CREDS]) {
                    sh '''
                        git clone git@github.com:asbass/k8s.git
                        cd k8s
                        sed -i "s|image:.*|image: ${IMAGE_NAME}:latest|g" backend/deployment.yaml
                        git config user.email "jenkins@jenkins.com"
                        git config user.name "Jenkins"
                        git add backend/deployment.yaml
                        git commit -m "Update image by Jenkins #${BUILD_NUMBER}"
                        git push origin main
                    '''
                }
            }
        }
    }
}
