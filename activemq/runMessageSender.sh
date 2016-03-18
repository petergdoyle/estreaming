#!/bin/sh

pwd=$PWD
script_path=$( cd $(dirname $0) ; pwd -P )

while true; do
  echo -e "*** Select the Messaging Provider Type *** \n \
  1) ActiveMQ JMS (AMQ) \
  "
#  2) Artimis (AMQ derivative) \n \
#  3) Apollo (AMQ derivative) \n \
#  4) Kafka \n \
#  5) ØMQ (zero mq) \n \
#  6) Spring Integration \n \
#  7) MQ Series JMS \n \
#  8) HornetMQ \
#  8) RabbitMQ \
  read opt
  case $opt in
      1)
      connection_factory_class_name='com.cleverfishsoftware.spring.xd.jms.sender.ActiveMQConnectionFactoryProvider'
      default_broker_url='tcp://localhost:61616'
      break
      ;;
      *)
      echo "not currently supported, try again."
      ;;
  esac
done

read -e -p "Enter the broker url : " -i $default_broker_url broker_url
read -e -p "Enter the activmq queue name: " -i "airshop" queue_name
read -e -p "Enter the number of messages to generate (per second): " -i "10" message_rate
read -e -p "Enter the max number of message (0 for unlimited): " -i "0" message_limit
if [ "$message_limit" -eq "0" ]; then
  read -e -p "Enter a timeout value in seconds (0 for unlimited): " -i "0" message_timeout
  if [ "$message_timeout" -gt "0" ]; then
    timeout='timeout '$message_timeout's'
  fi
else
  timeout='time'
fi
read -e -p "Enter the message size (bytes): " -i "1000" message_size
number_of_cores=$(grep -c ^processor /proc/cpuinfo)
read -e -p "Enter number of threads (1-$number_of_cores): " -i "$number_of_cores" number_of_threads
while true; do
  echo -e "*** Select the Payload Record Generator Type *** \n \
  1) Airline Flight Search Data (csv) \n \
  2) Car Rental Availablity Data (edifact)\n \
  3) Hotel Room Availablity Data (edifact)\n \
  4) Ones and Zeros (0,1,0,1,0...) \n \
  5) Lorem-ipsum (Lorem ipsum dolor...)\n \
  6) Comma Separated Integers (12,31,2,32...) \n \
  7) Smileys (☺☻☺☻☺☻...)\
  "
  read opt
  case $opt in
      1)
      payload_generator_class_name='com.cleverfishsoftware.spring.xd.jms.sender.airline.AirlineDataPayloadGenerator'
      break
      ;;
      4)
      payload_generator_class_name='com.cleverfishsoftware.spring.xd.jms.sender.OnesAndZerosPayloadGenerator'
      break
      ;;
      7)
      payload_generator_class_name='com.cleverfishsoftware.spring.xd.jms.sender.SmileysPayloadGenerator'
      break
      ;;
      *)
      echo "not currently supported, try again."
      ;;
  esac
done

read -e -p "Display output to console (y/n) " -i "n" console_output

cmd="$timeout \
java -cp .:target/spring-xd-jms-sender-1.0-SNAPSHOT.jar \
com.cleverfishsoftware.spring.xd.jms.sender.RunMessageSender \
$broker_url \
$queue_name \
$connection_factory_class_name \
$payload_generator_class_name \
$message_rate \
$message_limit \
$message_size \
$number_of_threads \
$console_output"

echo $cmd
eval $cmd

#echo "JMS MessageSender started on PID $!"
cd $pwd
