#!/bin/sh
. ../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh

img_name='estreaming/nodejs'

container_name='estreaming_nodejs_streaming_api_server'
workdir="--workdir=\"/nodejs/streaming_api_server/\""
start_cmd="/usr/bin/npm start"
network="$network_native"
docker_run
