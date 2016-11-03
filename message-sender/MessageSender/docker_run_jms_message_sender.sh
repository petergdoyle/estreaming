#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh

img_name='estreaming/activemq'

. ./message_sender_functions.sh

read -e -p "Enter the number of instances to run: " -i "1" instances

#ip=$(grep estreaming-activemq-server /etc/hosts |awk '{ print $1 }')
#cmd="time java -cp .:target/spring-xd-jms-sender-1.0-SNAPSHOT.jar com.cleverfishsoftware.spring.xd.jms.sender.jms.RunJMSMessageSender tcp://$ip:61616 airshop com.cleverfishsoftware.spring.xd.jms.sender.jms.ActiveMQConnectionFactoryProvider com.cleverfishsoftware.spring.xd.jms.sender.generator.air.AirlineDataPayloadGenerator 1 10 1000 2 n"

for i in $(eval echo "{1..$instances"});   do
  container_name='estreaming-activemq-message-sender'_$i
  start_cmd="$cmd"
  network="$network_native"
  docker_run
done
