FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar Websocket.jar
VOLUME /config
ENTRYPOINT ["java","-jar","Websocket.jar"]
EXPOSE 8080