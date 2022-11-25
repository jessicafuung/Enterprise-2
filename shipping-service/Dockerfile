FROM amazoncorretto:11
COPY ./target/shipping-service-0.0.1-SNAPSHOT.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "/tmp/shipping-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8090