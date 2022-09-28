FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
EXPOSE 8085
ARG JAR_FILE=target/pubsub-app.jar
COPY ${JAR_FILE} pubsub-app.jar
ENTRYPOINT ["java","-jar","pubsub-app.jar"]