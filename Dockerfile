FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY /src /backend/src
COPY /pom.xml /backend
RUN mvn -f /backend/pom.xml clean package -Dmaven.test.skip

FROM openjdk:22-ea-21-jdk-slim
EXPOSE 8080
COPY --from=build /backend/target/*.jar backend.jar
ENTRYPOINT ["java", "-jar", "/backend.jar"]