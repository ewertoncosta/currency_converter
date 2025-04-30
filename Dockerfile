FROM maven:3.8.7-openjdk-18-slim
WORKDIR /app

ENV TZ=America/Sao_Paulo
RUN apt-get update \
 && apt-get install -y --no-install-recommends tzdata \
 && ln -sf /usr/share/zoneinfo/$TZ /etc/localtime \
 && echo $TZ > /etc/timezone \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B \
 && cp target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=docker"]