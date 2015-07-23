FROM ubuntu

# Install nginx
RUN apt-get update && apt-get install -y nginx

VOLUME /var/log/nginx/api-shop-front:/var/log/nginx

# Map logs
RUN ln -sf /dev/stdout /var/log/nginx/access.log
RUN ln -sf /dev/stderr /var/log/nginx/error.log

# Remove default site
RUN rm -rf /etc/nginx/sites-enabled/default

EXPOSE 80 443
COPY conf/ /etc/nginx
COPY sites-enabled/ /etc/nginx/sites-enabled
RUN ln -sf /etc/nginx/sites-enabled/api.shop.com /etc/nginx/sites-available/api.shop.com

CMD ["nginx", "-g", "daemon off;"]