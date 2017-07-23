#!/bin/sh

topic='use-case-1'
read -e -p "Enter the topic: " -i "$topic" topic
broker_host_port='localhost:9092'
read -e -p "Enter the broker host/port: " -i "$broker_host_port" broker_host_port
offset='--from-beginning'
read -e -p "Enter the offset: " -i "$offset" offset
consumer_group_id='consumer-group-1'
read -e -p "Enter the consumer group id: " -i "$consumer_group_id" consumer_group_id
consumer_id='consumer-1'
read -e -p "Enter the consumer id: " -i "$consumer_id" consumer_id
sleep='0'
read -e -p "Enter consumer process latency (in millis): " -i "$sleep" sleep
console_output='n'
read -e -p "Display output to console (y/n) " -i "$console_output" console_output

fout=$topic'_'$consumer_group_id'.out'
if [ -f "$fout" ]; then
  read -e -p "Overwrite file $fout?: " -i "n" fout_overwrite
  if [[ "$fout_overwrite" == 'n' || "$fout_overwrite" == 'N' ]]; then
    append_overwrite='>>'
    echo "Output will be appended to $fout"
  else
    append_overwrite='>'
    echo "Output will be written to $fout"
  fi
else
  append_overwrite='>'
  echo "Output will be written to $fout"
fi

#cmd="java -jar target/KafkaConsumer-1.0-SNAPSHOT.jar $broker_host_port $consumer_group_id $consumer_id $topic $sleep $append_overwrite $fout"
cmd="java -cp .:target/MessageReceiver-1.0-SNAPSHOT.jar com.cleverfishsoftware.estreaming.kafka.consumer.RunKafkaConsumer \
$broker_host_port \
$consumer_group_id \
$consumer_id \
$topic \
$sleep \
$console_output"
echo "$cmd"
eval "$cmd"
