FROM openjdk:17
COPY /target/mailservice-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app/
CMD ["java","-jar","app.jar"]
