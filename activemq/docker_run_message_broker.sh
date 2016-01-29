#!/bin/sh
. ../scripts/lib/docker_functions.sh
. ../scripts/lib/network_functions.sh


img_name='estreaming/activemq'

container_name='estreaming_activemq_broker'
start_cmd="java -cp .:./spring-xd-jms-sender-1.0-SNAPSHOT.jar com.cleverfishsoftware.spring.xd.jms.sender.RunBroker"
network="$network_native"
docker_run
