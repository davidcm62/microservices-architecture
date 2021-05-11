FROM openjdk:15-jdk-alpine

#jar must be built with ./gradelew build
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

CMD java -jar /app.jar