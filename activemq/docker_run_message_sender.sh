#!/bin/sh
. ../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh

img_name='estreaming/activemq'

./RunMessageSenderArgs.sh

read -e -p "Enter the number of instances to run: " -i "1" instances

for i in $(eval echo "{1..$instances"});   do
  container_name='estreaming_activemq_message_sender'_$i
  start_cmd="java -cp .:./spring-xd-jms-sender-1.0-SNAPSHOT.jar \
  com.cleverfishsoftware.spring.xd.jms.sender.RunMessageSenderArgs \
  $broker_url $queue_name $message_rate $console_output"
  network="$network_native"
  docker_run
done
