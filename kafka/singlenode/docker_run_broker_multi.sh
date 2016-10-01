#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/kafka'
container_name='estreaming_kafka_broker'

start_cmd='bin/kafka-server-start.sh config/server.properties'

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

for i in $(eval echo "{1..$instances"});   do
  container_name='estreaming_kafka_broker'_$i
  # copy the default properties and make the appropriate config settings
  cp config/server.properties config/server$i.properties
  sed -i sed -i "/broker.id.*/c broker.id=$i"  config/server$i.properties
  # specify the appropriate start command
  start_cmd="bin/kafka-server-start.sh config/server$i.properties"
  docker_run
done

docker_run 9092
