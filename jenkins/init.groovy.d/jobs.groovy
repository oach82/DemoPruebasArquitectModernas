import jenkins.model.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition

def instance = Jenkins.get()
def jobName = "BankingLabPipeline"

if (instance.getItem(jobName) == null) {

    println "--> Creando job: ${jobName}"

    def pipelineScript = """
pipeline {
    agent any

    environment {
        ZAP_URL = "http://zap:8083"
        AUTH_URL = "http://auth-service:8080"
        TRANSFER_URL = "http://transfer-service:8080"
    }

    stages {

        stage('API Tests - Postman') {
            steps {
                sh '''
                newman run tests/auth.postman_collection.json
                '''
            }
        }

        stage('ZAP Spider - Auth') {
            steps {
                sh '''
                curl "\$ZAP_URL/JSON/spider/action/scan/?url=\$AUTH_URL&recurse=true"
                '''
            }
        }

        stage('Wait Spider - Auth') {
            steps {
                sh '''
                while true; do
                  STATUS=\$(curl -s "\$ZAP_URL/JSON/spider/view/status/" | tr -dc '0-9')
                  echo "Spider Auth: \$STATUS%"
                  [ "\$STATUS" = "100" ] && break
                  sleep 5
                done
                '''
            }
        }

        stage('ZAP Spider - Transfer') {
            steps {
                sh '''
                curl "\$ZAP_URL/JSON/spider/action/scan/?url=\$TRANSFER_URL&recurse=true"
                '''
            }
        }

        stage('Wait Spider - Transfer') {
            steps {
                sh '''
                while true; do
                  STATUS=\$(curl -s "\$ZAP_URL/JSON/spider/view/status/" | tr -dc '0-9')
                  echo "Spider Transfer: \$STATUS%"
                  [ "\$STATUS" = "100" ] && break
                  sleep 5
                done
                '''
            }
        }

        stage('ZAP Active Scan - Auth') {
            steps {
                sh '''
                curl "\$ZAP_URL/JSON/ascan/action/scan/?url=\$AUTH_URL&recurse=true"
                '''
            }
        }

        stage('ZAP Active Scan - Transfer') {
            steps {
                sh '''
                curl "\$ZAP_URL/JSON/ascan/action/scan/?url=\$TRANSFER_URL&recurse=true"
                '''
            }
        }

        stage('Wait Active Scans') {
            steps {
                sh '''
                while true; do
                  STATUS=\$(curl -s "\$ZAP_URL/JSON/ascan/view/status/" | tr -dc '0-9')
                  echo "Active Scan: \$STATUS%"
                  [ "\$STATUS" = "100" ] && break
                  sleep 10
                done
                '''
            }
        }

        stage('Generate ZAP Report') {
            steps {
                sh '''
                curl "\$ZAP_URL/OTHER/core/other/htmlreport/" -o zap-security-report.html
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '*.html'
        }
    }
}
"""

    def job = instance.createProject(WorkflowJob, jobName)
    job.setDefinition(new CpsFlowDefinition(pipelineScript, true))
    job.save()

    println "--> Job ${jobName} creado correctamente"
}