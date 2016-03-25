#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh

img_name='estreaming/activemq'

read -e -p "Enter the number of instances to run: " -i "1" instances

./RunMessageSender.sh

for i in $(eval echo "{1..$instances"});   do
  container_name='estreaming_activemq_message_sender'_$i
  start_cmd="$cmd"
  network="$network_native"
  docker_run
done
