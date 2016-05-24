#!/bin/sh

docker exec -ti estreaming_ibm_mq8_broker getent group mqm |cut -d':' -f 4-
