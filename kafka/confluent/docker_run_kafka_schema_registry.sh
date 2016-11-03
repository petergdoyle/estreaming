#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh

img_name='estreaming/confluent'
container_name='estreaming-kafka-schema-registry'

start_cmd='bin/schema-registry-start etc/schema-registry/schema-registry.properties'

read -e -p "Run native networking mode(y/n): " -i "y" native
if [[ "$native" == "n" || "$native" == "N" ]]; then
  read -e -p "Enter the network port number: " -i "8081" port
  network="-p 0.0.0.0:$port"
  links="-link estreaming-kafka-zk -link estreaming-kafka-broker"
else
  network="$network_native"
fi

volumes="-v $PWD:/docker"
network="$network"
docker_run $port
