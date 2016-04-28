#!/bin/sh

read -e -p "Enter the topic: " -i "splash" topic
read -e -p "Enter the zk host/port: " -i "localhost:2181" zk_host_port

docker exec -ti estreaming_kafka_broker bin/kafka-console-consumer.sh --zookeeper $zk_host_port --topic $topic --from-beginning
