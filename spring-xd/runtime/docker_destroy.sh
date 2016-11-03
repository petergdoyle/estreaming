#!/bin/bash
. ../../scripts/lib/docker_functions.sh

container_name="estreaming-xd-singlenode"

docker_destroy $container_name
