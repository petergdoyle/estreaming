#!/bin/sh

read -e -p "Enter the destination to consume (queue://<queue-name>, topic://<topic-name>): " -i "queue://queue1" destination

docker exec -ti estreaming-activemq-server bin/activemq consumer --destination $destination
