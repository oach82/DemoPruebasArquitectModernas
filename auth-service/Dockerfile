# Etapa 1: Build con Maven
FROM docker.io/library/maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime con JRE (más liviano que JDK)
FROM docker.io/library/eclipse-temurin:17-jre
WORKDIR /app
# Copiamos el JAR ejecutable generado por Spring Boot
COPY --from=builder /app/target/auth-service-1.0.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]