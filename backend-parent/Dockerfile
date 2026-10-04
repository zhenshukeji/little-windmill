FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/windmill-community-parent-api.jar app.jar
EXPOSE 8184
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
