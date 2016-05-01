#!/bin/sh

javaOpts=""

while true; do
  echo -e "*** Select the Messaging Provider Type *** \n \
  1) ActiveMQ JMS \n \
  2) IBM MQ JMS \n \
  3) Kafka \
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
      default_broker_url='tcp://localhost:61616'
      read -e -p "Enter the broker url : " -i $default_broker_url broker_url
      read -e -p "Enter the queue name: " -i "queue1" queue_name
      message_sender_builder_class_name="com.cleverfishsoftware.loadgenerator.sender.jms.JMSMessageSenderBuilder"
javaOpts="-DLoadGenerator.ConnectionFactoryProvider.class=com.cleverfishsoftware.loadgenerator.sender.jms.amq.ActiveMQConnectionFactoryProvider \
-DLoadGenerator.ConnectionFactoryProvider.broker_url=$broker_url \
-DLoadGenerator.ConnectionFactoryProvider.queue_name=$queue_name"
      break
      ;;
      3)
      default_broker_url='localhost:9092'
      read -e -p "Enter the broker url : " -i $default_broker_url broker_url
      read -e -p "Enter the topic name: " -i "topic1" topic_name
      message_sender_builder_class_name="com.cleverfishsoftware.loadgenerator.sender.kafka.KafkaMessageSenderBuilder"
javaOpts="-DLoadGenerator.KafkaMessageSenderBuilder.broker_url=$broker_url \
-DLoadGenerator.KafkaMessageSenderBuilder.topic_name=$topic_name"
      break
      ;;
      *)
      echo "not currently supported, try again."
      ;;
  esac
done

read -e -p "Enter the number of messages to generate (per second): " -i "10" message_rate
read -e -p "Enter the max number of messages (0 for unlimited): " -i "0" message_limit
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
  echo -e "*** Select the Payload Generator Type *** \n \
 1) Airline Flight Search Data (csv) \n \
 2) Car Availablity Data (edifact)\n \
 3) Hotel Room Availablity Data (edifact)\n \
 4) Sequential Number (0000000001,0000000002,0000000003...) \n \
 5) Ones and Zeros (0,1,0,1,0...) \n \
 6) Lorem-ipsum (Lorem ipsum dolor...)\n \
 7) Comma Separated Integers (12,31,2,32...) \n \
 8) Smileys (☺☻☺☻☺☻...) \n \
10) FileSystem Payload Generator \
"
  read opt
  case $opt in
      1)
      payload_generator_builder_class_name='com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataPayloadGeneratorBuilder'
      break
      ;;
      2)
      payload_generator_builder_class_name='com.cleverfishsoftware.loadgenerator.payload.car.EdifactCarPayloadGeneratorBuilder'
      break
      ;;
      4)
      payload_generator_builder_class_name='com.cleverfishsoftware.loadgenerator.payload.SequentialNumberPayloadGeneratorBuilder'
      break
      ;;
      5)
      payload_generator_builder_class_name='com.cleverfishsoftware.loadgenerator.payload.OnesAndZerosPayloadGeneratorBuilder'
      break
      ;;
      8)
      payload_generator_builder_class_name='com.cleverfishsoftware.loadgenerator.payload.SmileysPayloadGeneratorBuilder'
      break
      ;;
      10)
      payload_generator_builder_class_name="com.cleverfishsoftware.loadgenerator.payload.FileSystemPayloadGeneratorBuilder"
      while true; do
        read -e -p "Enter a file or directory location: " -i "" file_system_payload_generator_fn
        if [[ -f $file_system_payload_generator_fn || -d $file_system_payload_generator_fn ]]; then
javaOpts="$javaOpts -DLoadGenerator.FileSystemPayloadGenerator.file=$file_system_payload_generator_fn"
          break
        fi
      done
      break
      ;;
      *)
      echo "That option is not currently supported, try again."
      ;;
  esac
done

read -e -p "Display output to console (y/n) " -i "n" console_output

cmd="$timeout \
java -Xms1G -Xmx1G \
$javaOpts \
-cp .:target/LoadGenerator-1.0-SNAPSHOT.jar \
com.cleverfishsoftware.loadgenerator.MessageSenderRunner \
$message_sender_builder_class_name \
$payload_generator_builder_class_name \
$message_rate \
$message_limit \
$message_size \
$number_of_threads \
$console_output"

echo $cmd
