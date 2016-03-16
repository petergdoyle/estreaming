#!/bin/sh

pwd=$PWD
script_path=$( cd $(dirname $0) ; pwd -P )

read -e -p "Enter the broker url : " -i "tcp://localhost:61616" broker_url
read -e -p "Enter the activmq queue name: " -i "airshop" queue_name
read -e -p "Enter the number of messages to generate (per second): " -i "10" message_rate
read -e -p "Enter the max number of message (0 for unlimited): " -i "0" message_limit
read -e -p "Enter the message size (bytes): " -i "1000" message_size
number_of_cores=$(grep -c ^processor /proc/cpuinfo)
read -e -p "Enter number of threads (1-$number_of_cores): " -i "$number_of_cores" number_of_threads
while true; do
echo -e "*** Select the JMS Connection Factory Provider Type *** \n\
1) ActiveMQConnectionFactoryProvider \n\
"
read opt
case $opt in
    1)
    connectionFactoryClassName='com.cleverfishsoftware.spring.xd.jms.sender.sample.ActiveMQConnectionFactoryProvider'
    break
    ;;
    *)
    echo "invalid option"
    ;;
esac
done

while true; do
echo -e "*** Select the Payload Generator Type *** \n\
1) AirlineDataPayloadGenerator \n\
2) RandomCharacterPayloadGenerator \n\
"
read opt
case $opt in
    1)
    payloadGeneratorClassName='com.cleverfishsoftware.spring.xd.jms.sender.sample.AirlineDataPayloadGenerator'
    break
    ;;
    2)
    payloadGeneratorClassName='com.cleverfishsoftware.spring.xd.jms.sender.sample.RandomCharacterPayloadGenerator'
    break
    ;;
    *)
    echo "invalid option"
    ;;
esac
done

read -e -p "Display output to console (y/n) " -i "n" console_output

old_cmd="java -cp .:$script_path/target/spring-xd-jms-sender-1.0-SNAPSHOT.jar \
  com.cleverfishsoftware.spring.xd.jms.sender.RunMessageSenderArgs \
  $broker_url $queue_name $message_rate $console_output"

cmd="java -cp .:target/spring-xd-jms-sender-1.0-SNAPSHOT.jar \
com.cleverfishsoftware.spring.xd.jms.sender.sample.RunMessageSender \
$broker_url \
$queue_name \
$connectionFactoryClassName \
$payloadGeneratorClassName \
$message_rate \
$message_limit \
$message_size \
$number_of_threads \
$console_output"

echo $cmd
eval $cmd

#echo "JMS MessageSender started on PID $!"
cd $pwd
