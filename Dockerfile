FROM anapsix/alpine-java:latest 
COPY target/library-afterprereq-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9001 
ENTRYPOINT java -jar ./app.jar
