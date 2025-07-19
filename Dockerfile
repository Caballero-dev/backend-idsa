FROM maven:3.9.11-amazoncorretto-21 AS build
WORKDIR /app
COPY ./ /app/
RUN ls
RUN mvn clean package -DskipTests

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
RUN ls
EXPOSE ${SERVER_PORT:-8080}
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# docker build -t idsa-back .
# docker run -d -p 8080:8080 --name idsa-app-api --env-file .env idsa-back