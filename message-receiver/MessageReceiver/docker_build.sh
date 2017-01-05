#!/bin/sh

. ../../scripts/lib/docker_functions.sh

no_cache=$1

img_name='estreaming/java-message-receiver'

./clean_and_build.sh

docker_build $no_cache
