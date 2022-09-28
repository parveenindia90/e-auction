FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
EXPOSE 8081
ARG JAR_FILE=target/seller-app.jar
COPY ${JAR_FILE} seller-app.jar
ENTRYPOINT ["java","-jar","seller-app.jar"]