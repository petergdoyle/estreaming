#!/bin/sh
. ../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh


img_name='estreaming/mongodb'

container_name='estreaming-mongodb-server'
start_cmd="--dbpath /data/db --smallfiles"
network_port_mapped="-p 0.0.0.0:27017:27017 \
-h $container_name.dkr"
network="$network_port_mapped"
docker_run
