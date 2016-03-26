#!/bin/sh

git clone https://github.com/ibm-messaging/mq-docker.git

docker build -t="ibm/mq8" ./mq-docker/8.0.0/

mkdir lib/

docker build -t="estreaming/mq8" .
