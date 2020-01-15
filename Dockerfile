FROM java:8
VOLUME /tmp
ADD target/simian*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
