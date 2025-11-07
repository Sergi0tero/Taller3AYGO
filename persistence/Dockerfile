# ==========================
# Etapa 1: Construcción
# ==========================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Define el directorio de trabajo
WORKDIR /app

# Copia el contenido del proyecto al contenedor
COPY . .

# Compila el proyecto y genera el .jar (sin ejecutar tests)
RUN mvn clean package -DskipTests

# ==========================
# Etapa 2: Ejecución
# ==========================
FROM eclipse-temurin:21-jdk

# Define el directorio donde se ejecutará el .jar
WORKDIR /app

# Copia el .jar generado desde la etapa anterior
COPY --from=builder /app/target/persistence-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto del servidor Spring Boot
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
