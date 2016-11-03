#!/bin/sh
. ../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh


img_name='estreaming/mongodb'

container_name='estreaming-mongodb-server'
start_cmd="--dbpath /data/db --smallfiles"
network="$network_native"
docker_run
