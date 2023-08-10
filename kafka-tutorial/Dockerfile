FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
EXPOSE 8080
COPY target/kafka-tutorial-0.0.1-SNAPSHOT.jar /app/kafka-tutorial-0.0.1-SNAPSHOT.jar
ENTRYPOINT exec java $JAVA_OPTS -jar kafka-tutorial-0.0.1-SNAPSHOT.jar