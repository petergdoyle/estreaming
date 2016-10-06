#!/bin/sh
cd $(dirname $0)
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/kafka'

read -e -p "Run native networking mode(y/n): " -i "y" native
if [[ "$native" == "n" || "$native" == "N" ]]; then
  read -e -p "Enter the network port number: " -i "9092" port
  network="-p 0.0.0.0:$port"
  links="-link estreaming_kafka_zk"
else
  network="$network_native"
fi

volumes="-v $PWD/shared:/shared"
network="$network"

read -e -p "Enter the number of brokers to run: " -i "1" instances
port=9092
for i in $(eval echo "{1..$instances"});   do
  container_name='estreaming_kafka_broker'_$i
  start_cmd="bin/kafka-server-start.sh config/server$i.properties"
  docker_run "$port"
  port=$((port+1))
done
