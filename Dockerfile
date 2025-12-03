FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/abc-telecom-postpaid-billing.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]