#!/bin/sh

docker rmi $(docker images |grep estreaming | awk '{print $3}')
