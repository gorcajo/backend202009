FROM openjdk:11
WORKDIR /usr/src/myapp
COPY ./target/*.jar app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=live", "app.jar"]
