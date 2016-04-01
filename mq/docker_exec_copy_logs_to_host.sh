
#!/bin/sh

read -e -p "Enter the Queue Manager name: " -i "QM1" qm

sudo mkdir -p logs/var/mqm/errors
docker exec -ti estreaming_ibm_mq8_broker find /var/mqm/errors -name AMQERR01.LOG -type f -exec cp -fv {} /docker/logs/var/mqm/errors \;

sudo mkdir -p logs/var/mqm/qmgrs/$qm/errors/
docker exec -ti estreaming_ibm_mq8_broker find /var/mqm/qmgrs/$qm/errors/ -name AMQERR01.LOG -type f -exec cp -fv {} /docker/logs/var/mqm/qmgrs/$qm/errors \;

tree logs
