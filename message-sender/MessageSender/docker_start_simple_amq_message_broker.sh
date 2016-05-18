#!/bin/sh
docker start estreaming_activemq_broker

docker logs -f estreaming_activemq_broker
