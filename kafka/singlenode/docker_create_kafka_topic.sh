#!/bin/sh

read -e -p "Enter the topic name: " -i "use-case-1" topic
read -e -p "Enter the zk host/port: " -i "localhost:2181" zk_host_port
read -e -p "Enter replcation factor: " -i "1" replication_factor
read -e -p "Enter number of partitions: " -i "1" partitions

docker exec -ti estreaming_kafka_broker bin/kafka-topics.sh --create --zookeeper $zk_host_port --replication-factor $replication_factor --partitions $partitions --topic $topic
