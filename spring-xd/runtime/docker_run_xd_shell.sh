#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh

img_name='estreaming/springxd'
container_name='estreaming_xd_shell'
start_cmd="/bin/bash"
network_port_mapped="-h $container_name.dkr"
network="$network_port_mapped"
docker_run
