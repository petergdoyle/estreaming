#!/bin/sh
. ../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh

img_name='estreaming/springxd'
container_name='estreaming_xd_singlenode'
start_cmd="xd-singlenode"
network="$network_native"
docker_run
