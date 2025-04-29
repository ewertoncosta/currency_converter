# Stage 1: Build the application
FROM eclipse-temurin:18-jdk-alpine AS build
WORKDIR /app

# Copy Maven wrapper and project files
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copy source code and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:18-jre-alpine
WORKDIR /app

# Set timezone
ENV TZ=America/Sao_Paulo
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/${TZ} /etc/localtime && \
    echo ${TZ} > /etc/timezone

# Add the application JAR
COPY --from=build /app/target/currency-converter.jar /app/currency-converter.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/currency-converter.jar", "--spring.profiles.active=docker"]