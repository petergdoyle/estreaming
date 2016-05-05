#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/kafka'
container_name='estreaming_kafka_zk'

start_cmd='bin/zookeeper-server-start.sh config/zookeeper.properties'

read -e -p "Run native networking mode(y/n): " -i "y" native
if [[ "$native" == "n" || "$native" == "N" ]]; then
  read -e -p "Enter the network port number: " -i "2181" port
  network="-p 0.0.0.0:$port"
else
  network="$network_native"
fi

network="$network"
docker_run 2181
