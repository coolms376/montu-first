FROM openjdk:latest
COPY /target/app.jar /app/app.jar
WORKDIR /app/
CMD ["java","-jar","app.jar"]
