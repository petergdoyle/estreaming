#!/bin/sh

javaOpts=''

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
      read -e -p "Enter the queue name: " -i "QUEUE1" queue_name
      message_sender_builder_class_name="com.cleverfishsoftware.loadgenerator.sender.jms.JMSMessageSenderBuilder"
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.class="com.cleverfishsoftware.loadgenerator.sender.jms.amq.ActiveMQConnectionFactoryProvider"'
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.broker_url="'$broker_url'"'
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.queue_name="'$queue_name'"'
      break
      ;;
      2)
      message_sender_builder_class_name="com.cleverfishsoftware.loadgenerator.sender.jms.JMSMessageSenderBuilder"
      #message_sender_builder_class_name="com.cleverfishsoftware.loadgenerator.sender.jms.mq.IBMMQMessageSenderBuilder"
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.class="com.cleverfishsoftware.loadgenerator.sender.jms.mq.IBMMQConnectionFactoryProvider"'

      read -e -p "Enter the host name: " -i "localhost" mq_host
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.mq_host="'$mq_host'"'
      read -e -p "Enter the port number: " -i "1414" mq_port
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.mq_port="'$mq_port'"'
      read -e -p "Enter the Queue Manager name: " -i "QM1" mq_queue_manager
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.mq_queue_manager="'$mq_queue_manager'"'
      read -e -p "Enter the Channel name: " -i "SYSTEM.DEF.SVRCONN" mq_channel
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.mq_channel="'$mq_channel'"'
      read -e -p "Enter the Queue name: " -i "QUEUE1" queue_name
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.queue_name="'$queue_name'"'

      read -e -p "Is security enabled on the Queue Manager? (y/n) " -i "y" mq_security_enabled
      if [[ "$mq_security_enabled" == "y" || "$mq_security_enabled" == "Y"  ]]; then
        read -e -p "Enter the user name: " -i "$USER" mq_user
        javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.mq_user="'$mq_user'"'
        read -e -p "Enter the user password: " -i "passw0rd" mq_pw
        javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.mq_pw="'$mq_pw'"'
      fi

      break
      ;;
      3)
      default_broker_url='localhost:9092'
      read -e -p "Enter the broker url : " -i $default_broker_url broker_url
      read -e -p "Enter the topic name: " -i "use-case-1" topic_name
      message_sender_builder_class_name="com.cleverfishsoftware.loadgenerator.sender.kafka.KafkaMessageSenderBuilder"
      javaOpts=$javaOpts' -DLoadGenerator.KafkaMessageSenderBuilder.broker_url="'$broker_url'"'
      javaOpts=$javaOpts' -DLoadGenerator.KafkaMessageSenderBuilder.topic_name="'$topic_name'"'
      break
      ;;
      *)
      echo "That option is not currently supported, try again."
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
number_of_cores=$(grep -c ^processor /proc/cpuinfo)
read -e -p "Enter number of threads (1-$number_of_cores): " -i "$number_of_cores" number_of_threads
while true; do
  read -e -p "*** Select the Payload Generator Type ***
 1) Airline Flight Search Data (csv)
 2) Car Availablity Data (edifact)
 3) Hotel Room Availablity Data (edifact)
 4) Sequential Number (0000000001,0000000002,0000000003...)
 5) Ones and Zeros (0,1,0,1,0...)
 6) Lorem-ipsum (Lorem ipsum dolor...)
 7) Comma Separated Integers (12,31,2,32...)
 8) Smileys (☺☻☺☻☺☻...)
10) FileSystem Payload Generator
" -i "4" opt
  #read opt
  case $opt in
      1)
      payload_generator_builder_class_name='com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataPayloadGeneratorBuilder'
      read -e -p "What type of output (csv or json)?: " -i "json" format_type
      if [[ "$format_type" == "json" || "$format_type" == "JSON" ]]; then
        airlineDataFormatterType = "com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataFormatterJSON"
      else
        airlineDataFormatterType = "com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataFormatterCSV"
      fi
      javaOpts=$javaOpts' -DLoadGenerator.AirlineDataPayloadGeneratorBuilder.airlineDataFormatterType="'$airlineDataFormatterType'"'
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
          javaOpts=$javaOpts' -DLoadGenerator.FileSystemPayloadGenerator.file="'$file_system_payload_generator_fn'"'
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

read -e -p "Pad message size? (content type natural size varies): " -i "n" pad_message_size
if [[ "$pad_message_size" == 'y' || "$pad_message_size" == 'Y' ]]; then
  read -e -p "message size in bytes (0 for natural size): " -i "0" message_size
else
  message_size='0'
fi
read -e -p "Add a message batch identifier (will appear in message): " -i "n" add_batch_identfier
if [[ "$add_batch_identfier" == 'y' || "$add_batch_identfier" == 'Y' ]]; then
  read -e -p "Specify identifier: " -i " ><>" batch_identifier
  javaOpts=$javaOpts' -DLoadGenerator.PayloadGenerator.batch_identifier="'$batch_identifier'"'
else
  batch_identifier=''
fi


read -e -p "Display output to console (y/n) " -i "n" console_output

cmd="$timeout \
java -Xms1G -Xmx1G \
$javaOpts \
-cp .:target/MessageSender-1.0-SNAPSHOT.jar \
com.cleverfishsoftware.loadgenerator.MessageSenderRunner \
$message_sender_builder_class_name \
$payload_generator_builder_class_name \
$message_rate \
$message_limit \
$message_size \
$number_of_threads \
$console_output"

echo $cmd
