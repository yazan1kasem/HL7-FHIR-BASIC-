FROM node:lts AS client-build
WORKDIR /app
COPY spengerclient/package*.json ./
RUN npm install
COPY spengerclient/ ./
RUN npm run build --prod

FROM maven:3.8.4-openjdk-11 AS backend-build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY --from=client-build /app/dist/spengerclient ./src/main/resources/static
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=backend-build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]