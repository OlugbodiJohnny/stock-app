FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine

COPY --from=build /app/target/stock-app-2-0.0.1-SNAPSHOT.jar stock-app-2-0.0.1-SNAPSHOT.jar

COPY --from=build /app/src/main/resources/application-prod.properties application.properties

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
#COPY target/payment-gateway-demo-0.0.1-SNAPSHOT.jar .

#ARG JAR_FILE=payment-gateway-demo-0.0.1-SNAPSHOT.jar

EXPOSE 5000

ENV SERVER_PORT ${{ secrets.SERVER_PORT }}

ENV SPRING_DATASOURCE_URL ${{ secrets.SPRING_DATASOURCE_URL }}

ENV SPRING_DATASOURCE_USERNAME ${{ secrets.SPRING_DATASOURCE_USERNAME }}

ENV SPRING_DATASOURCE_PASSWORD ${{ secrets.SPRING_DATASOURCE_PASSWORD }}

ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT ${{ secrets.SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT }}

ENV SPRING_JPA_HIBERNATE_DDL_AUTO ${{ secrets.SPRING_JPA_HIBERNATE_DDL_AUTO }}

ENV SPRING_JPA_HIBERNATE_SHOW_SQL ${{ secrets.SPRING_JPA_HIBERNATE_SHOW_SQL }}

ENV SPRING_ADMIN_EMAIL ${{ secrets.SPRING_ADMIN_EMAIL }}

ENV SPRING_ADMIN_PASSWORD ${{ secrets.SPRING_ADMIN_PASSWORD }}

ENV SPRING_MAIL_HOST ${{ secrets.SPRING_MAIL_HOST }}

ENV SPRING_MAIL_PORT ${{ secrets.SPRING_MAIL_PORT }}

ENV SPRING_MAIL_USERNAME ${{ secrets.SPRING_MAIL_USERNAME }}

ENV SPRING_MAIL_PASSWORD ${{ secrets.SPRING_MAIL_PASSWORD }}

ENV SPRING_MAIL_SMTP_AUTH ${{ secrets.SPRING_MAIL_SMTP_AUTH }}

ENV SPRING_MAIL_SMTP_STARTTLS ${{ secrets.SPRING_MAIL_SMTP_STARTTLS }}

ENV SPRING_MAIL_SMTP_SLL_ENABLE ${{ secrets.SPRING_MAIL_SMTP_SLL_ENABLE }}

ENV TOKEN_EXPIRATION ${{ secrets.TOKEN_EXPIRATION }}

ENV SECRET_KEY ${{ secrets.SECRET_KEY }}

ENV POLYGON_API_KEY ${{ secrets.POLYGON_API_KEY }}

ENV POLYGON_BASE_URL ${{ secrets.POLYGON_BASE_URL }}

ENV DEV_EMAIL ${{ secrets.DEV_EMAIL }}

ENV DEV_PASSWORD ${{ secrets.DEV_PASSWORD }}

ENV SWAGGER_URL ${{ secrets.SWAGGER_URL }}

ENV JAVA_OPTS "-Xmx512m -Xmx256m -Xmx6G"

ENTRYPOINT ["java", "-jar", "stock-app-2-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]
#ENTRYPOINT ["java", "-jar", "stock-app-2-0.0.1-SNAPSHOT.jar", "--spring.config.name=application", "--spring.config.location=./", "--spring.profiles.active=prod"]
# Set the image name
#LABEL maintainer="olugbodi-johnny"
#LABEL version="1.0"
#LABEL description="stock-app-2"
#LABEL name="stock-app-2"