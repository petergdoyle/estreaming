#!/bin/sh

docker start estreaming-xd-singlenode
docker start estreaming-xd-shell

docker start estreaming-activemq-broker

docker start estreaming-mongodb-server
docker start estreaming-nodejsstreaming-api-server
docker start estreaming-nodejs-runtime

docker start estreaming-springboot-runtime

docker start estreaming-activemq-message-sender
