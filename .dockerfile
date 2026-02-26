# Imagem base com Java
FROM openjdk:17-jdk-slim

# Diretório dentro do container
WORKDIR /app

# Copia o jar para dentro do container
COPY target/*.jar app.jar

# Porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]