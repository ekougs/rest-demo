FROM java:8

COPY target/rest-demo-0.0.1.jar /usr/local/bin/api-shop-server.jar

EXPOSE 8080

CMD ["java", "-jar", "/usr/local/bin/api-shop-server.jar"]