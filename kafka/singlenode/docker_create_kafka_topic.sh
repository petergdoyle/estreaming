#!/bin/sh

read -e -p "Enter the topic name: " -i "use-case-1" topic
read -e -p "Enter the zk host/port: " -i "localhost:2181" zk_host_port
read -e -p "Enter replcation factor: " -i "1" replication_factor
read -e -p "Enter number of partitions: " -i "1" partitions

brokers=$(docker ps |grep kafka-broker| awk '{print $NF}')
echo -e "The following Kafka brokers were found: \n$brokers"
brokers_arr=($brokers)
read -e -p "Pick a broker instance (any one):" -i ${brokers_arr[0]} selected_broker

cmd="docker exec -ti $selected_broker bin/kafka-topics.sh --create --zookeeper $zk_host_port --replication-factor $replication_factor --partitions $partitions --topic $topic"
echo "$cmd"
eval "$cmd"
