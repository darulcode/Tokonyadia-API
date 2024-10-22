FROM openjdk:17-alpine
WORKDIR /app
COPY target/tokonyadia-0.0.1-SNAPSHOT.jar tokonyadia.jar
CMD ["java", "-jar", "tokonyadia.jar"]