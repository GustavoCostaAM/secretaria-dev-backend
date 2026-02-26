# ---------- ETAPA 1: BUILD ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia tudo
COPY . .

# Gera o jar
RUN mvn clean package -DskipTests


# ---------- ETAPA 2: RUNTIME ----------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copia o jar gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]