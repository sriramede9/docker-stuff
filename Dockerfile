FROM eclipse-temurin:25

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8099

ENTRYPOINT ["java", "-jar", "app.jar"]