#!/bin/bash

echo "--> This script stops and removes all containers and images for shop api"
echo ""
echo ""

function container_exists {
    local __container_runs=$(docker inspect --format="{{ .State.Running }}" ${1} 2> /dev/null)
    if [ $? != 0 ] ; then
        return 1
    fi
    if [[ "${__container_runs}" == "true" || "${__container_runs}" == "false" ]] ; then
        return 0
    fi
    return 1
}

function container_runs {
    local __container=$1
    container_exists ${__container}
    if [ $? != 0 ] ; then
        return 1
    fi
    local __container_runs=$(docker inspect --format="{{ .State.Running }}" $1 2> /dev/null)
    if [ "${__container_runs}" == "true" ] ; then
        return 0
    fi
    return 1
}

function remove_container_if_exists {
    local __container=$1
    container_runs ${__container}
    if [ $? == 0 ] ; then
        echo ""
        echo "--> Stopping container" ${__container}
        (docker stop ${__container})
        echo ""
        echo ""
    fi
    container_exists ${__container}
    if [ $? == 0 ] ; then
        echo ""
        echo "--> Removing container" ${__container}
        (docker rm ${__container})
        echo ""
        echo ""
    fi
}

# Stopping and removing existing containers
remove_container_if_exists "api-shop-front"
remove_container_if_exists "api-shop-load-balancer"
remove_container_if_exists "api-shop-server-1"
remove_container_if_exists "api-shop-server-2"
remove_container_if_exists "api-shop-server-3"
remove_container_if_exists "api-shop-mongo"