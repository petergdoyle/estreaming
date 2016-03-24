#!/bin/sh

. ../scripts/lib/docker_functions.sh

no_cache=$1

img_name='estreaming/springxd'

docker_build $no_cache
