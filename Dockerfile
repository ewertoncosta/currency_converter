# Stage 1: Build the application
FROM eclipse-temurin:18-jdk-alpine AS build
WORKDIR /app

COPY src ./src
RUN ./mvnw clean package -DskipTests

RUN ls -l /app/target

ENV TZ=America/Sao_Paulo
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/${TZ} /etc/localtime && \
    echo ${TZ} > /etc/timezone

COPY --from=build /app/target/currency-converter.jar /app/currency-converter.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/currency-converter.jar", "--spring.profiles.active=docker"]