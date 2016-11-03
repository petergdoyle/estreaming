#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh

img_name='estreaming/springxd'
container_name='estreaming-xd-shell'
start_cmd="/bin/bash"
network="$network_native"
links="--link estreaming-xd-singlenode:estreaming-xd-singlenode"
docker_run
