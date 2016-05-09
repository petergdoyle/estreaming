#!/bin/sh

read -e -p "Enter the zk host/port: " -i "localhost:2181" zk_host_port
docker exec estreaming_kafka_broker bin/kafka-topics.sh --list --zookeeper $zk_host_port
