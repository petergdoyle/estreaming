#!/bin/sh

read -e -p "Enter the Queue Manager name: " -i "QM1" qm

read -e -p "Enter the Queue name: " -i "QUEUE1" qu

docker exec \
  --ti \
  estreaming_ibm_mq8_broker \
  echo "DIS QL($qu) CURDEPTH" |runmqsc $qm
