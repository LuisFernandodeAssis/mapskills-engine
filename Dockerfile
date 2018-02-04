FROM openjdk:8-jdk-alpine
RUN apk update && apk add --no-cache bash
ADD target/mapskills-engine.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]