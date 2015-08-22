#!/bin/sh

pwd=$PWD
script_path=$( cd $(dirname $0) ; pwd -P )

java -cp .:$script_path/target/spring-xd-jms-sender-1.0-SNAPSHOT.jar  com.cleverfishsoftware.spring.xd.jms.sender.RunMessageSenderArgs $1 $2

echo "JMS MessageSender started on PID $!" 
cd $pwd

