#!/bin/sh

read -e -p "Enter the Queue Manager name: " -i "QM1" qm

sudo docker exec \
  --tty \
  --interactive \
  estreaming_ibm_mq8_broker \
  runmqsc $qm
