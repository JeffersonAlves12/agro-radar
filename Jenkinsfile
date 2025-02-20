pipeline {
    agent any
    
    tools {
        maven 'Maven 3.8.4'
        jdk 'JDK 17'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout do código fonte
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                // Navega para o diretório correto do projeto Java
                dir('agro-radar/agro-radar-java') {
                    // Executa o build Maven
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        
        stage('Test') {
            steps {
                dir('agro-radar/agro-radar-java') {
                    // Executa os testes
                    sh 'mvn test'
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                dir('agro-radar/agro-radar-java') {
                    // Constrói a imagem Docker
                    sh 'docker build -t agro-radar-java .'
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline executado com sucesso!'
        }
        failure {
            echo 'Pipeline falhou!'
        }
    }
}