#!/bin/bash
. ../../scripts/lib/docker_functions.sh

container_name="estreaming_xd_singlenode"

docker_destroy $container_name
