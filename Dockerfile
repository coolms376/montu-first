FROM openjdk:latest
RUN mvn package -DskipTests=true
CMD ["java","-jar","target/app.jar"]
