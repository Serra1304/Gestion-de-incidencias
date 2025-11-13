# Etapa 1: build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias antes de copiar el código
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y construimos el jar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiamos el jar generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto interno del contenedor
EXPOSE 8080

# Comando para iniciar Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
