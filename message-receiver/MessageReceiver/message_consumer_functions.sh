#!/bin/sh

javaOpts=''

while true; do
  echo -e "*** Select the Messaging Provider Type *** \n \
  1) ActiveMQ JMS \n \
  2) IBM MQ JMS \
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
      default_broker_url='tcp://localhost:61616?jms.prefetchPolicy.all=1'
      read -e -p "Enter the broker url : " -i $default_broker_url broker_url
      read -e -p "Enter the queue name: " -i "QUEUE1" queue_name
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.class="com.cleverfishsoftware.loadgenerator.sender.jms.amq.ActiveMQConnectionFactoryProvider"'
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.broker_url="'$broker_url'"'
      javaOpts=$javaOpts' -DLoadGenerator.ConnectionFactoryProvider.queue_name="'$queue_name'"'
      break
      ;;
      2)
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
      *)
      echo "That option is not currently supported, try again."
      ;;
  esac
done

read -e -p "Enter the consumer id: " -i "consumer-1" consumer_id
javaOpts=$javaOpts' -DRunJMSConsumer.MessageConsumer.consumer_id="'$consumer_id'"'
read -e -p "Enter consumer process latency (in millis): " -i "0" sleep
javaOpts=$javaOpts' -DRunJMSConsumer.MessageConsumer.sleep_time="'$sleep'"'
read -e -p "Display output to console (y/n) " -i "n" console_output
noisy="false"
if [[ $console_output == "Y" || $console_output == "y" ]]; then
  noisy="true"
fi
javaOpts=$javaOpts' -DRunJMSConsumer.MessageConsumer.noisy="'$noisy'"'

cmd="java -Xms1G -Xmx1G \
$javaOpts \
-cp .:target/MessageReceiver-1.0-SNAPSHOT.jar \
com.cleverfishsoftware.estreaming.jms.consumer.RunJMSConsumer"

echo $cmd
