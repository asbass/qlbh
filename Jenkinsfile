pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    imagePullPolicy: Always
    command:
    - /busybox/cat
    tty: true
    volumeMounts:
    - name: docker-config
      mountPath: /kaniko/.docker/
  volumes:
  - name: docker-config
    secret:
      secretName: docker-config
      items:
      - key: .dockerconfigjson
        path: config.json
'''
        }
    }

    environment {
        IMAGE_NAME = "taibaton/ltw"
        GIT_CREDS = 'github-pat-credentials-id'
    }

    stages {
        stage('Maven Build') {
            steps {
                // Thay 'maven' bằng tên container chứa công cụ maven của bạn
                container('maven') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build & Push with Kaniko') {
            steps {
                container('kaniko') {
                    sh """
                        /kaniko/executor --context=`pwd` \
                        --dockerfile=Dockerfile \
                        --destination=${IMAGE_NAME}:${BUILD_NUMBER} \
                        --destination=${IMAGE_NAME}:latest \
                        --skip-tls-verify
                    """
                }
            }
        }

        stage('Update K8s Repo (GitOps)') {
            steps {
                sshagent([GIT_CREDS]) {
                    sh '''
                        git clone git@github.com:asbass/k8s.git
                        cd k8s
                        sed -i "s|image:.*|image: ${IMAGE_NAME}:${BUILD_NUMBER}|g" backend/deployment.yaml
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
