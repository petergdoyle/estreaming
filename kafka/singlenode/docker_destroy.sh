#!/bin/sh
. ../../scripts/lib/docker_functions.sh

brokers=$(docker ps -a |grep kafka-broker| awk '{print $NF}')
for broker in ${brokers[@]}
do
  docker_destroy "${broker}"
done
docker_destroy estreaming-kafka-zk
