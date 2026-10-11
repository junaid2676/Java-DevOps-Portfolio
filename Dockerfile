# Stage 1: Build the Java application using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight JRE image
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the built jar from the builder stage (adjust jar name if needed)
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
