#!/bin/sh
. ../scripts/lib/docker_functions.sh

container_name="estreaming_mongodb_server"

docker_destroy $container_name
