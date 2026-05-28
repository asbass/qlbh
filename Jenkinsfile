pipeline {
    agent any
    tools {
        maven 'Maven-3.9' 
    }
    environment {
        DOCKERHUB_USER = 'taibaton'
        IMAGE_NAME = "${DOCKERHUB_USER}/ltw"
        DOCKER_CREDS = 'docker' 
        GIT_CREDS = 'f78957c5-d4aa-4ff1-bc1e-2f9de9c0d8b9'
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Đảm bảo clone sạch sẽ vào workspace
                cleanWs()
                git branch: 'main', url: 'https://github.com/asbass/qlbh.git'
            }
        }
        
        stage('Build & Push') {
            steps {
                script {
                     // Định nghĩa đường dẫn Java 21 và đường dẫn tới bin của nó
                    def java21 = '/usr/lib/jvm/java-21-openjdk-amd64'
                    // Dùng lệnh find để xem pom.xml thực sự nằm ở đâu
                    sh 'find . -name "pom.xml"'
                    withEnv(["JAVA_HOME=${java21}", "PATH+JAVA=${java21}/bin"]) {
                        sh 'java -version' // Kiểm tra nhanh xem nó đã nhận đúng Java 21 chưa
                        sh 'mvn clean package -DskipTests'
                    }
                    // Nếu find cho ra kết quả như: ./backend/pom.xml
                    // Bạn phải cd vào thư mục đó trước khi chạy maven
                    // Giả sử pom.xml nằm trong thư mục 'backend'                        
                        // Build Docker tại thư mục có chứa Dockerfile
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
                        rm -rf k8s-repo # Xóa folder cũ nếu có
                        git clone git@github.com:asbass/k8s.git k8s-repo
                        cd k8s-repo
                        # Cập nhật file deployment.yaml
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
