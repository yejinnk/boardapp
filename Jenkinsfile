pipeline {
    agent any

    environment {
        PORT_ADMIN = '8090'
    }    

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    // 클론할 디렉토리를 명시적으로 지정
                    // -d: 지정된 경로가 디렉토리인지 확인.
                    // [ ! -d "myproject" ] : "myproject"가 존재하지 않거나 디렉토리가 아닌 경우 참(true).                    
                    sh '''
                    if [ ! -d "myproject" ]; then
                        echo "myproject directory is not exist."
                    else
                    echo "Repository already cloned in myproject directory."
                        rm -rf ./myproject
                    fi
                    
                    git clone -b master https://github.com/inwookbaek/20240809_green_cicd.git myproject
                    '''
                }
            }
        }

        stage('Check if App is Running') {
            steps {
                script {
                    sh '''
                    # Check if app.jar is running
                    if pgrep -f app.jar > /dev/null; then
                        # 프로세스 죽이기
                        pkill -9 -f app.jar
                    else
                        echo "app.jar is not running."
                    fi
                    '''
                }
            }
        }

        stage('Change Directory and SpringBoot App Build') {
            steps {
                script {
                    // Change to the "myproject" directory and perform a task
                    sh '''
                    cd myproject
                    # pwd  # 확인용: 현재 디렉토리 출력
                    # ls   # 확인용: 디렉토리 내용 출력
                    
                    # ./gradlew 실행권한 부여
                    chmod +x ./gradlew
                    
                    # jar build
                    ./gradlew clean build
                    
                    # 필요한 빌드 명령 실행 (예: Docker 빌드, Gradle, Maven 등)
                    # 예: Docker 빌드
                    # docker build -t myimage:latest .
                    '''
                }
            }
        }  
        
        stage('Run Java App in Background') {
            steps {
                script {
                    sh '''
                    # Navigate to the project directory
                    cd ./myproject/build/libs
                    # pwd 
                    # ls
                    
                    # Run the Java application in the background
                    # nohup은 no hang up 의 약자 입니다. 해석 그대로 "끊지마!" 의미
                    # 중단 없이 실행하고자 하는 프로그램 명령어 앞에 "nohup" 만 붙여주면 된다.
                    # 참고 사이트 : https://joonyon.tistory.com/entry/쉽게-설명한-nohup-과-백그라운드-명령어-사용법
                    # 0 : 표준 입력/ 1 : 표준 출력 / 2 : 표준 에러
                    # 2>&1 : 표준출력과 표준에러를 같은 파일에 쓰기
                    export JENKINS_NODE_COOKIE=dontKillMe
                    nohup java -jar app.jar > app.log 2>&1 &
                    # nohup java -jar $WORKSPACE/myproject/build/libs/app.jar > $WORKSPACE/myproject/build/libs/app.log 2>&1 &
                    # nohup java -Dserver.port=${PORT_ADMIN} -Dspring.profiles.active=dev -Dfile.encoding=UTF-8 -jar app.jar > app.log 2>&1 &
                    
                    # Print confirmation
                    echo "Java application started in the background."
                    '''
                }
            }
        }

        stage('Docker Image Build!!') {
            steps {
                script {
                    try {
                      sh '''
                        cd ./myproject
                        # pwd 
                        # ls
                        # sudo docker rmi $(sudo docker images -q)
                        # sudo docker rm -f $(sudo docker ps -aq)
                      '''
                     } catch(e) {
                       echo "docker container or image delete fail!!"
                     }                    
                    sh '''
                      echo "docker image build & docker run!!!!"
                      docker build -t spring-board .
                      docker run -dit --name webapp -p 9090:8090 spring-board
                    '''
                }
            }
        }          
    }             
}