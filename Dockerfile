FROM eclipse-temurin:17

# Diretório de trabalho dentro do container
WORKDIR /first-test-aluno

# Copia o JAR que foi criado no pipeline anterior
COPY target/*.jar app.jar

# Porta da aplicação (ajuste se necessário)
EXPOSE 8080

# Comando para executar
CMD ["java", "-jar", "app.jar"]