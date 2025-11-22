# 1. Usamos una imagen de Maven para construir el proyecto
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Usamos una imagen ligera de Java para ejecutarlo
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Copiamos el archivo .jar generado en el paso anterior
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]