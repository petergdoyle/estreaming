#!/bin/sh

read -e -p "Enter the Queue Manager name: " -i "QM1" qm

docker exec \
  -ti \
  estreaming_ibm_mq8_broker \
  /bin/sh -c "runmqsc $qm < /etc/mqm/config_clauth_off.mqsc"
