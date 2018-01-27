FROM openjdk:8-jdk-alpine
RUN apk update && apk add --no-cache bash
ADD target/mapskills-2.0.0.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]