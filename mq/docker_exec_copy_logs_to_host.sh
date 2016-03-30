
#!/bin/sh

sudo mkdir -p logs/var/mqm/errors
docker exec -ti estreaming_ibm_mq8_broker find /var/mqm/errors -name AMQERR01.LOG -type f -exec cp -fv {} /docker/logs/var/mqm/errors \;

sudo mkdir -p logs/var/mqm/qmgrs/QM1/errors/
docker exec -ti estreaming_ibm_mq8_broker find /var/mqm/qmgrs/QM1/errors/ -name AMQERR01.LOG -type f -exec cp -fv {} /docker/logs/var/mqm/qmgrs/QM1/errors \;

tree logs
