FROM openjdk:11
CMD ["mkdir", "app"]
WORKDIR app/
COPY target/buying-currency-service-0.0.1-SNAPSHOT.jar app/app.jar
#EXPOSE 8083
CMD ["java", "-jar", "app/app.jar"]