FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine as base
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src
RUN chmod +x ./mvnw
RUN ./mvnw clean install -DskipTests=true

FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine as production
WORKDIR /app
EXPOSE 8080
COPY --from=base /app/target/*.jar ./EcommerceNashtechBackend.jar
CMD ["java", "-jar", "/app/EcommerceNashtechBackend.jar"]
