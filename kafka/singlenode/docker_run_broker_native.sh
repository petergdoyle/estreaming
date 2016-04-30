#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/kafka'
container_name='estreaming_kafka_broker'

start_cmd='bin/kafka-server-start.sh config/server.properties'

network="$network_native"
docker_run 9092
