#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh

img_name='estreaming/springxd'
container_name='estreaming_xd_singlenode'
start_cmd="xd-singlenode"
network_port_mapped="-p 0.0.0.0:9393:9393 \
-h $container_name.dkr"
volumes="$shared_volume_base"
network="$network_port_mapped"
docker_run
