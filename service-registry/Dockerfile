FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
EXPOSE 8761
ARG JAR_FILE=target/service-discovery.jar
COPY ${JAR_FILE} service-discovery.jar
ENTRYPOINT ["java","-jar","service-discovery.jar"]