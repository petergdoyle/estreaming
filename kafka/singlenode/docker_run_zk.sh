#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/kafka'
container_name='estreaming_kafka_zk'

start_cmd='bin/zookeeper-server-start.sh config/zookeeper.properties'

#network_port_mapped="-p 0.0.0.0:2181:2181 \
#-p 0.0.0.0:9092:9092 \
#-h kafka_source.dkr"
#volumes="-v $PWD:/docker"

network="$network_native"
docker_run 2181,9092
