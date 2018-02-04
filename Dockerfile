FROM openjdk:8-jdk-alpine
ADD target/mapskills-engine.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]