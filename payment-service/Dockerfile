FROM amazoncorretto:11
COPY ./target/payment-service-0.0.1-SNAPSHOT.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "payment-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8085