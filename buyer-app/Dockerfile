FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
EXPOSE 8082
ARG JAR_FILE=target/buyer-app.jar
COPY ${JAR_FILE} buyer-app.jar
ENTRYPOINT ["java","-jar","buyer-app.jar"]