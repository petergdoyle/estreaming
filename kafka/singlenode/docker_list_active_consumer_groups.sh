#!/bin/sh

read -e -p "Enter the bootstrap server: " -i "localhost:9092" bootstrap_server

brokers=$(docker ps -a |grep kafka_broker|  awk '{print $10;}')
echo -e "The following Kafka brokers were found: \n$brokers"
brokers_arr=($brokers)
read -e -p "Pick a broker instance (any one):" -i ${brokers_arr[0]} selected_broker

docker exec -ti $selected_broker bin/kafka-run-class.sh kafka.admin.ConsumerGroupCommand --list --new-consumer --bootstrap-server $bootstrap_server
