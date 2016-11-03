#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh


img_name='estreaming/activemq'

container_name='estreaming-activemq-broker'
start_cmd="java \
-cp .:./spring-xd-jms-sender-1.0-SNAPSHOT.jar \
com.cleverfishsoftware.spring.xd.jms.broker.RunBroker"
network_port_mapped="-p 0.0.0.0:61616:61616 \
-p 0.0.0.0:8161:8161 \
-h activemq.dkr"
network="$network_port_mapped"
docker_run 8161,61616
