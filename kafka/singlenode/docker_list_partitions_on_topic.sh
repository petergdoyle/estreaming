#!/bin/sh

read -e -p "Enter the topic name: " -i "use-case-1" topic
read -e -p "Enter the zk host/port: " -i "localhost:2181" zk_host_port

brokers=$(docker ps |grep kafka-broker| awk '{print $NF}')
echo -e "The following Kafka brokers were found: \n$brokers"
brokers_arr=($brokers)
read -e -p "Pick a broker instance (any one):" -i ${brokers_arr[0]} selected_broker

docker exec $selected_broker bin/kafka-topics.sh --describe --topic $topic --zookeeper $zk_host_port
