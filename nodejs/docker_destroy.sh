#!/bin/sh

. ../scripts/lib/docker_functions.sh

container_name="estreaming-nodejsstreaming-api-server"

docker_destroy $container_name
