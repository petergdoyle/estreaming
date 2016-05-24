#!/bin/sh

#pwd=$PWD
#script_path=$( cd $(dirname $0) ; pwd -P )

. ./message_consumer_functions.sh

eval $cmd

#echo "JMS MessageSender started on PID $!"
#cd $pwd
