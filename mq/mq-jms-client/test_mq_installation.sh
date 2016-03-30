#!/bin/sh

mvn clean install
pgm='MQJMSMessageSender'
echo "Running program $pgm"
read -e -p "Enter the host name: " -i "localhost" host
read -e -p "Enter the port number: " -i "1414" port
read -e -p "Enter the Queue Manager name: " -i "QM1" qm
read -e -p "Enter the Channel name: " -i "SYSTEM.DEF.SVRCONN" ch
read -e -p "Enter the Queue name: " -i "QUEUE1" qu
read -e -p "Enter the Message text: " -i "Hello World" msg

read -e -p "Is security enabled on the Queue Manager? (y/n) " -i "n" sec_enabled
if [ "$sec_enabled" == "y" ]; then
  read -e -p "Enter the user name: " -i "QUEUE1" user
  read -e -p "Enter the user password: " -i "Hello World" pw
fi

java \
  -cp .:target/mq-jms-client-1.0-SNAPSHOT.jar \
  com.cleverfishsoftware.jms.mq.$pgm \
  $host $port $qm $ch $qu $msg $sec_enabled $user $pw
