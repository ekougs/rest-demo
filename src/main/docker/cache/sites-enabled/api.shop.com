server {
    listen 80;
    location / {
        proxy_pass http://api-shop-load-balancer/;
        proxy_set_header Host $host;
        proxy_cache cache;
        proxy_cache_valid 2m;
        expires 2m;
        proxy_cache_use_stale error timeout invalid_header updating;
    }
}