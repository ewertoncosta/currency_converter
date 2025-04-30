FROM maven:3.8.7-openjdk-18-slim
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests -B

ENV TZ=America/Sao_Paulo
RUN apt-get update && apt-get install -y tzdata \
 && cp /usr/share/zoneinfo/$TZ /etc/localtime \
 && echo $TZ > /etc/timezone

EXPOSE 8080
ENTRYPOINT ["java","-jar","target/currency-converter.jar","--spring.profiles.active=docker"]