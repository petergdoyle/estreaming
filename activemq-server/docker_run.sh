#!/bin/sh
. ../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh

img_name='estreaming/activemq-server'
container_name='estreaming_activemq_server'
start_cmd="bin/activemq start"
network_port_mapped="-p 0.0.0.0:61616:61616 \
-h $container_name.dkr"
network="$network_port_mapped"
docker_run
