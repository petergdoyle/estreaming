#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh

img_name='estreaming/springboot'

container_name='estreaming-springboot-runtime'
start_cmd="spring run dashboard.groovy"
network="$network_native"
docker_run
