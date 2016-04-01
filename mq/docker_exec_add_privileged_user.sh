#!/bin/sh

#add a system privileged (member of mqm) user

read -e -p "Enter the user name: " -i "$USER" user
read -e -p "Enter the user password: " -i "passw0rd" password
docker exec \
  -ti \
  estreaming_ibm_mq8_broker \
  /bin/sh -c "useradd $user -G mqm && echo $user:$password | chpasswd"
