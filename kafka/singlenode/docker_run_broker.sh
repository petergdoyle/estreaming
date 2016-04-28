#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/kafka'
container_name='estreaming_kafka_broker'

start_cmd='bin/kafka-server-start.sh config/server.properties'

#network_port_mapped="-p 0.0.0.0:2181:2181 \
#-p 0.0.0.0:9092:9092 \
#-h kafka_source.dkr"
#volumes="-v $PWD:/docker"

network="$network_native"
docker_run 2181,9092
