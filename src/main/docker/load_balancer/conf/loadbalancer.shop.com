upstream api-shop-servers {
    server api-shop-server-1:8080;
    server api-shop-server-2:8080;
    server api-shop-server-3:8080;
}

server {
    listen 80;
    location / {
        proxy_pass http://api-shop-servers;
    }
}