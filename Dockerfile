FROM openjdk:17-jdk
# jar를 가지고 오는 도커 파일
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} board.jar

ENTRYPOINT ["java", "-jar", "/board.jar"]