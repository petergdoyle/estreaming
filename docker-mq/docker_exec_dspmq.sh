#!/bin/sh

sudo docker exec \
  --tty \
  --interactive \
  estreaming_ibm_mq8_broker \
  dspmq
