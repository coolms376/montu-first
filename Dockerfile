FROM openjdk:latest
RUN ['mvn' ,'package']
CMD ["java","-jar","/target/app.jar"]
