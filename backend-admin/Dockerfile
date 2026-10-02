FROM openjdk:8-jre-alpine
WORKDIR /app
COPY kg-admin/target/kg-admin.jar app.jar
EXPOSE 8179
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
