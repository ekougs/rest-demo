FROM java:8

COPY build/install/rest-demo /usr/local/bin/api-shop-server

EXPOSE 8080

CMD ["bash", "/usr/local/bin/api-shop-server/bin/rest-demo"]