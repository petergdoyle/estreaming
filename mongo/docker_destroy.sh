#!/bin/sh
. ../scripts/lib/docker_functions.sh

container_name="estreaming-mongodb-server"

docker_destroy $container_name
