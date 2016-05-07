#!/bin/sh

read -e -p "Enter the bootstrap server: " -i "localhost:9092" bootstrap_server

docker exec -ti estreaming_kafka_broker bin/kafka-run-class.sh kafka.admin.ConsumerGroupCommand --list --new-consumer --bootstrap-server $bootstrap_server
