#!/bin/sh
. ../../scripts/lib/docker_functions.sh

brokers=$(docker ps -a |grep kafka-broker| awk '{print $NF}')
for each in ${brokers[@]}
do
  docker_destroy "${each}"
done
zks=$(docker ps -a |grep kafka-zk| awk '{print $NF}')
for each in ${brokers[@]}
do
  docker_destroy "${zks}"
done
