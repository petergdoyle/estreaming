#!/bin/sh

read -e -p "Enter the topic name: " -i "use-case-1" topic
read -e -p "Enter the zk host/port: " -i "localhost:2181" zk_host_port

docker exec estreaming_kafka_broker bin/kafka-topics.sh --describe --topic $topic --zookeeper $zk_host_port\
