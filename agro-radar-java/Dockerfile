# Etapa 1: Construção da aplicação com Maven
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências (cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila a aplicação
COPY src ./src
RUN mvn package -DskipTests

# Etapa 2: Cria a imagem final utilizando um runtime Java mais leve
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o jar gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta na qual a aplicação irá rodar (ajuste se necessário)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
