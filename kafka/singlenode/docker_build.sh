#!/bin/sh


. ../../scripts/lib/docker_functions.sh

no_cache=$1

zookeeper_port='2181'
read -e -p "Enter zookeeper port: " -i "y" zookeeper_port
read -e -p "Run native networking mode(y/n): " -i "y" native
if [[ "$native" == "n" || "$native" == "N" ]]; then
  read -e -p "Enter the zk host/port: " -i "estreaming-kafka-zk:$zookeeper_port" zk_host_port
else
  zk_host_port="localhost:$zookeeper_port"
fi
sed -i "s/clientPort=.*/clientPort=$zookeeper_port/g" config/zookeeper.properties

read -e -p "Enter Kafka Log Retention Hours: " -i "1" kafka_log_retention_hrs
read -e -p "Enter Kafka Log Retention Size (Mb): " -i "25" kafka_log_retention_size_mb
kafka_log_retention_size=$((1024*1024*$kafka_log_retention_size_mb))

read -e -p "Enter the number of brokers to run: " -i "1" instances
port=9092
for i in $(eval echo "{1..$instances"});   do
  cp config/server.properties.template config/server$i.properties
  sed -i "s/zookeeper.connect=.*/zookeeper.connect=$zk_host_port/g" config/server$i.properties
  sed -i "s/log.retention.hours=.*/log.retention.hours=$kafka_log_retention_hrs/g" config/server$i.properties
  sed -i "s/log.retention.bytes=.*/log.retention.bytes=$kafka_log_retention_size/g" config/server$i.properties
  sed -i "s/log.segment.bytes=.*/log.segment.bytes=$kafka_log_retention_size/g" config/server$i.properties
  sed -i "s/broker.id=.*/broker.id=$i/g" config/server$i.properties
  sed -i "s%listeners=.*%listeners=PLAINTEXT://:$port%g" config/server$i.properties
  sed -i "s%port=.*%port=$port%g" config/server$i.properties
  port=$((port+1))
done

img_name='estreaming/kafka'

docker_build $no_cache
