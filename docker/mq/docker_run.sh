#!/bin/sh

sudo sysctl fs.file-max=524288

docker run \
  --env LICENSE=accept \
  --env MQ_QMGR_NAME=QM1 \
  --volume /var/example:/var/mqm \
  --publish 0.0.0.0:1414:1414 \
  --detach \
  --name estreaming_ibm_mq8_broker \
  estreaming/mq8
