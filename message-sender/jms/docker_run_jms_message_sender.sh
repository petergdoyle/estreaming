#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh

. ./message_sender_functions.sh

read -e -p "Enter the number of instances to run: " -i "1" instances

for i in $(eval echo "{1..$instances"});   do
  container_name='estreaming_activemq_message_sender'_$i
  start_cmd="$cmd"
  network="$network_native"
  docker_run
done
