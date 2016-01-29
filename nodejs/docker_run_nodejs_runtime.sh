#!/bin/sh
. ../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh


img_name='estreaming/nodejs'

container_name='estreaming_nodejs_runtime'
start_cmd="/bin/bash"
network="$network_native"
docker_run
