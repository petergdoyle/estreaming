#!/bin/sh

. ../scripts/lib/docker_functions.sh

container_name="estreaming_nodejs_streaming_api_server"

docker_destroy $container_name
