FROM adoptopenjdk/openjdk11

MAINTAINER Selim BOUHASSATINE

VOLUME ["/episen-order"]

COPY target/order-service-0.0.1-SNAPSHOT.jar /episen-order/order-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/episen-order/order-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 9001