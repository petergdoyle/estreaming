#!/bin/sh

read -e -p "Enter the Queue Manager name: " -i "QM1" qm

docker exec -ti estreaming_ibm_mq8_broker /bin/sh -c "echo \"DIS QMGR\" |runmqsc $qm"  |egrep 'CHLAUTH|CONNAUTH'
