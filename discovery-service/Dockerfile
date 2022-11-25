FROM amazoncorretto:11
COPY ./target/discovery-service-0.0.1-SNAPSHOT.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "discovery-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8761
