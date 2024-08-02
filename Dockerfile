FROM openjdk:22-ea-21-jdk-slim

WORKDIR /app

COPY target/*.jar /app/api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]