#!/bin/sh


. ../../scripts/lib/docker_functions.sh

no_cache=$1


read -e -p "Run native networking mode(y/n): " -i "y" native
if [[ "$native" == "n" || "$native" == "N" ]]; then
  read -e -p "Enter the zk host/port: " -i "estreaming_kafka_zk:2181" zk_host_port
else
  zk_host_port="localhost:2181"
fi
sed -i "s/zookeeper.connect.*/zookeeper.connect=$zk_host_port/g" config/server.properties


read -e -p "Enter Kafka Log Retention Hours: " -i "1" kafka_log_retention_hrs
sed -i "s/log.retention.hours.*/log.retention.hours=$kafka_log_retention_hrs/g" config/server.properties


read -e -p "Enter Kafka Log Retention Size (Mb): " -i "25" kafka_log_retention_size_mb
kafka_log_retention_size=$((1024*1024*$kafka_log_retention_size_mb))
sed -i "s/log.retention.bytes.*/log.retention.bytes=$kafka_log_retention_size/g" config/server.properties
sed -i "s/log.segment.bytes.*/log.segment.bytes=$kafka_log_retention_size/g" config/server.properties



img_name='estreaming/kafka'

docker_build $no_cache
