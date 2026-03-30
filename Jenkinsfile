pipeline {
    agent none
    stages {
	matrix {
		axes {
			axis {
				name 'JDK_VERSION'
				values '21', '25'
			}
		}
		agent any
		tools {
			jdk "${JDK_VERSION}"
			gradle '9.4.1'
		}
		stages {
			stage("Checkout"){
				steps {
					checkout scm
				}
			}
			stage("Build plugin"){
				steps {
					script {
						if(isUnix()){
							sh "./gradlew build"
						} else {
							bat "./gradlew.bat build"
						}
					}
				}
			}
			stage("Upload artifact"){
				steps {
					script {
						if(isUnix()){
							sh "mkdir -p output"
							sh "cp build/libs/ShowCommandsPlugin.java output/ShowCommandsPlugin-jvm-${JDK_VERSION}.jar"
						} else {
							bat "if not exist output mkdir output"
							bat "copy build\\libs\\ShowCommandsPlugin.java output\\ShowCommandsPlugin-jvm-${JDK_VERSION}.jar"
						}
					}
					archiveArtifacts artifacts: "output/ShowCommandsPlugin-jvm-${JDK_VERSION}.jar", fingerprint: true
				}
			}
		}
	}
	}
}
