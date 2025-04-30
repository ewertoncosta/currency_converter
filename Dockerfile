FROM eclipse-temurin:18-jdk-alpine AS build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

RUN ls -l /app/target

FROM eclipse-temurin:18-jre-alpine
WORKDIR /app

ENV TZ=America/Sao_Paulo
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/${TZ} /etc/localtime && \
    echo ${TZ} > /etc/timezone

COPY --from=build /app/target/*.jar /app/currency-converter.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/currency-converter.jar", "--spring.profiles.active=docker"]