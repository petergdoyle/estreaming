#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/kafka'
container_name='estreaming_kafka_zk'

start_cmd='bin/zookeeper-server-start.sh config/zookeeper.properties'

network="$network_native"
docker_run 2181,9092
