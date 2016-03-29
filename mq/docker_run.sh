#!/bin/sh


sudo sysctl fs.file-max=524288

read -e -p "Enter the Queue Manager name: " -i "QM1" qm

read -e -p "Enter the port number: " -i "1414" port

docker run \
  --env LICENSE=accept \
  --env MQ_QMGR_NAME=$qm \
  --volume /var/example:/var/mqm \
  --volume $PWD:/docker \
  --publish 0.0.0.0:$port:$port \
  --detach \
  --name estreaming_ibm_mq8_broker \
  estreaming/mq8
