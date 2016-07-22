#!/bin/sh

if [ $# -lt 2 ]; then
  read -e -p "Enter the consumer group id: " -i "consumer-group-1" consumer_group_id
  read -e -p "Enter the bootstrap server: " -i "localhost:9092" bootstrap_server
else
  consumer_group_id=$1
  bootstrap_server=$2
fi

docker exec -ti estreaming_kafka_broker bin/kafka-consumer-groups.sh --new-consumer --bootstrap-server $bootstrap_server --describe --group $consumer_group_id
