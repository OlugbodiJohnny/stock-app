FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine

COPY --from=build /app/target/stock-app-2-0.0.1-SNAPSHOT.jar stock-app-2-0.0.1-SNAPSHOT.jar

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
#COPY target/payment-gateway-demo-0.0.1-SNAPSHOT.jar .

#ARG JAR_FILE=payment-gateway-demo-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENV SERVER_PORT ""
ENV JAVA_OPTS "-Xmx512m -Xmx256m -Xmx6G"

ENTRYPOINT ["java", "-Xmx256m", "-Xmx512m", "-jar", "stock-app-2-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]

# Set the image name
#LABEL maintainer="olugbodi-johnny"
#LABEL version="1.0"
#LABEL description="stock-app-2"
#LABEL name="stock-app-2"