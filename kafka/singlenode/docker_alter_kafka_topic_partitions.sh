#!/bin/sh
echo "Kafka does not currently support reducing the number of partitions for a topic or changing the replication factor."
read -e -p "Enter the topic name: " -i "use-case-1" topic
read -e -p "Enter the zk host/port: " -i "localhost:2181" zk_host_port
read -e -p "Enter number of partitions: " -i "1" partitions

docker exec -ti estreaming_kafka_broker bin/kafka-topics.sh --alter --partitions $partitions --topic $topic --zookeeper $zk_host_port
