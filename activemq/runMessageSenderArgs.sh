#!/bin/sh

pwd=$PWD
script_path=$( cd $(dirname $0) ; pwd -P )

read -e -p "Enter the broker url : " -i "tcp://localhost:61616" broker_url
read -e -p "Enter the activmq queue name: " -i "airshop" queue_name
read -e -p "Enter the number of messages to generate (per second): " -i "10" message_rate
read -e -p "Display output to console (y/n) " -i "n" console_output

java -cp .:$script_path/target/spring-xd-jms-sender-1.0-SNAPSHOT.jar \
  com.cleverfishsoftware.spring.xd.jms.sender.RunMessageSenderArgs \
  $broker_url $queue_name $message_rate $console_output

echo "JMS MessageSender started on PID $!"
cd $pwd
