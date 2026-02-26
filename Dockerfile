# Imagem base com Java (nova e válida)
FROM eclipse-temurin:17-jdk-jammy

# Diretório dentro do container
WORKDIR /app

# Copia o jar
COPY target/*.jar app.jar

# Porta da aplicação
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]