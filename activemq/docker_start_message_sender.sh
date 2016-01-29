#!/bin/sh
docker start estreaming_activemq_message_sender

docker logs -f estreaming_activemq_message_sender
