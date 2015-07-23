FROM ubuntu

# Install nginx
RUN apt-get update && apt-get install -y nginx

VOLUME /var/log/nginx/api-shop-load-balancer:/var/log/nginx

# Map logs
RUN ln -sf /dev/stdout /var/log/nginx/access.log
RUN ln -sf /dev/stderr /var/log/nginx/error.log

# Remove default site
RUN rm -rf /etc/nginx/sites-enabled/default

EXPOSE 80 443
COPY conf/loadbalancer.shop.com /etc/nginx/sites-enabled/loadbalancer.shop.com
RUN ln -sf /etc/nginx/sites-enabled/loadbalancer.shop.com /etc/nginx/sites-available/loadbalancer.shop.com

CMD ["nginx", "-g", "daemon off;"]