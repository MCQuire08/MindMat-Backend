pipeline {
  agent any
  environment {
    SONARQUBE = 'sonar-local'
  }
  options {
    timestamps()
  }
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Permisos gradlew (Linux)') {
      when { expression { return isUnix() } }
      steps {
        sh 'chmod +x gradlew || true'
      }
    }

    stage('Build & Test') {
      steps {
        sh './gradlew clean test jacocoTestReport -x spotlessApply || true'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'build/test-results/test/**/*.xml'
          publishHTML target: [
            reportName: 'JaCoCo',
            reportDir: 'build/reports/jacoco/test/html',
            reportFiles: 'index.html',
            keepAll: true,
            alwaysLinkToLastBuild: true
          ]
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv("${SONARQUBE}") {
          sh """
            ./gradlew sonarqube \
              -Dsonar.host.url=${env.SONAR_HOST_URL} \
              -Dsonar.login=${env.SONAR_AUTH_TOKEN}
          """
        }
      }
    }

    stage('Quality Gate') {
      steps {
        timeout(time: 10, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
  }

  post {
    always {
      archiveArtifacts allowEmptyArchive: true, artifacts: 'build/reports/**/*'
    }
  }
}
