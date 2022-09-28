FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
EXPOSE 8084
ARG JAR_FILE=target/api-gateway.jar
COPY ${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java","-jar","api-gateway.jar"]