#!/bin/sh
. ../../scripts/lib/docker_functions.sh

docker_destroy estreaming_kafka_broker
docker_destroy estreaming_kafka_zk