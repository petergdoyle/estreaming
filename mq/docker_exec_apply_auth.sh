#!/bin/sh

#add a system privileged (member of mqm) user

read -e -p "Enter the user name: " -i "vagrant" user
read -e -p "Enter the user password: " -i "passw0rd" password
docker exec \
  --ti \
  estreaming_ibm_mq8_broker \
  useradd $user -G mqm && echo $user:$password | chpasswd

#enhance the security on the channel

read -e -p "Enter the Queue Manager name: " -i "QM1" qm

docker exec \
  --ti \
  estreaming_ibm_mq8_broker \
  runmqsc $qm < /etc/mqm/config_clauth_on.mqsc
