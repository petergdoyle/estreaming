#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/kafka'
read -e -p "Enter consumer number: " -i "1" consumer_number
container_name='estreaming-kafka-consumer-'$consumer_number

start_cmd='bin/kafka-server-start.sh config/server.properties'

read -e -p "Run native networking mode(y/n): " -i "y" native
if [[ "$native" == "n" || "$native" == "N" ]]; then
  read -e -p "Enter the network port number: " -i "9092" port
  network="-p 0.0.0.0:$port"
  links="-link estreaming-kafka-zk"
else
  network="$network_native"
fi

volumes="-v $PWD/shared:/shared"
network="$network"
docker_run 9092
