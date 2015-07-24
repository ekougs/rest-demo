#!/bin/bash

echo "--> This script rebuilds and restarts all containers and images for shop api"
echo ""
echo ""

function rebuild_image {
    local __image=$1
    local __image_path=$2
    (docker rmi ${__image} &> /dev/null)
    echo ""
    echo "--> Building image" ${__image}
    (cd "${__image_path}" ; docker build -t ${__image} .)
    echo ""
    echo ""
}

# Stopping and removing existing containers
./stop-delete-shop-infrastructure.sh

# Rebuilding images
rebuild_image "api-shop-server" "."
rebuild_image "api-shop-load-balancer" "src/main/docker/load_balancer"
rebuild_image "api-shop-front" "src/main/docker/cache"

# Launching mongo
echo ""
echo "--> Launching api-shop-mongo container..."
# TODO mount volume on local directory
api_shop_mongo_container_id=$(docker run -d -P --name api-shop-mongo mongo:3.0.4 --storageEngine=wiredTiger)
echo ""
echo ""

# Launching 3 api servers
echo ""
echo "--> Launching 3 api-shop-server containers..."
api_shop_server_1_container_id=$(docker run -d -P --name api-shop-server-1 --link api-shop-mongo:api-shop-mongo api-shop-server)
api_shop_server_2_container_id=$(docker run -d -P --name api-shop-server-2 --link api-shop-mongo:api-shop-mongo api-shop-server)
api_shop_server_3_container_id=$(docker run -d -P --name api-shop-server-3 --link api-shop-mongo:api-shop-mongo api-shop-server)
echo ""
echo ""

# Launching load balancer
echo "--> Launching an api-shop-load-balancer container..."
api_shop_load_balancer_container_id=$(docker run -d -P --name api-shop-load-balancer --link api-shop-server-1:api-shop-server-1 --link api-shop-server-2:api-shop-server-2 --link api-shop-server-3:api-shop-server-3 api-shop-load-balancer)
echo ""
echo ""

# Launching load balancer
echo "--> Launching an api-shop-front (containing cache) container..."
api_shop_front_container_id=$(docker run -d -P --name api-shop-front --link api-shop-load-balancer:api-shop-load-balancer api-shop-front)
echo ""
echo ""

echo "api-shop-mongo container ID : " ${api_shop_mongo_container_id}
echo "api-shop-server-1 container ID : " ${api_shop_server_1_container_id}
echo "api-shop-server-2 container ID : " ${api_shop_server_2_container_id}
echo "api-shop-server-3 container ID : " ${api_shop_server_3_container_id}
echo "api-shop-load-balancer container ID : " ${api_shop_load_balancer_container_id}
echo "api-shop-front container ID : " ${api_shop_front_container_id}

