#!/bin/sh

read -e -p "Enter the topic: " -i "use-case-1" topic
read -e -p "Enter the broker host/port: " -i "localhost:9092" broker_host_port

docker exec -ti estreaming_kafka_broker bin/kafka-console-producer.sh --broker-list $broker_host_port --topic $topic
