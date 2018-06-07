pipeline {
    agent any
    tools { 
        maven 'mvn.3.5.3'
        jdk 'jdk8u172'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                ''' 
            }
        }
        
        stage ('Coverage') {
            steps {
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent test' 
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                    jacoco execPattern: '**/target/**.exec'
                }
            }
        }
        
        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.skip=true package' 
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        
        stage ('AcceptanceTest') {
            steps {
                sh 'mkdir testFolder'
                sh 'echo "Select * from wwa703; Create table" > testFolder/testFile.sql'
                
                script{
                	RESULT = sh (
   						script: 'java -jar target/itemComplianceValidator.jar ./testFolder',
    					returnStdout: true
					).trim()
					
                	if(!${RESULT} =~ '.*incluye la constante CREATE.*'){
                		error('Salida esperada no válida: ' + ${RESULT})
                	}
                }
            }
            
        }
    }
}
