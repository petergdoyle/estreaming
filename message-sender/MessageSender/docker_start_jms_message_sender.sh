#!/bin/sh
docker start estreaming-activemq-message-sender

docker logs -f estreaming-activemq-message-sender
