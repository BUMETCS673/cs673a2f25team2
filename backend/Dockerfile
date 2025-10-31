FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar (ensure Maven build completed before docker build)
COPY target/backend-0.1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

