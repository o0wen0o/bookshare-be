FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 8081
#WORKDIR /app
ARG JAR_FILE=target/bookshare-be-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]