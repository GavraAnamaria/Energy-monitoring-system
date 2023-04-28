#buildkit is activated by default on host docker
FROM maven:3.8.6-openjdk-11-slim AS builder
#WORKDIR /opt/automotive-bootcamp/
WORKDIR /opt/tictactoe/
COPY pom.xml pom.xml
RUN mvn -e -B dependency:resolve
COPY src/ src/
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
WORKDIR /opt/tictactoe/
COPY --from=builder /opt/tictactoe/target/*.jar tictatoe.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tictatoe.jar"]