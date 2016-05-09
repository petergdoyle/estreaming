#!/bin/sh

read -e -p "Enter the topic: " -i "use-case-1" topic
read -e -p "Enter the broker host/port: " -i "localhost:9092" broker_host_port
read -e -p "Enter the offset: " -i "--from-beginning" offset
read -e -p "Enter the consumer group id: " -i "consumer-group-1" consumer_group_id
read -e -p "Enter consumer process latency (in millis): " -i "0" sleep

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

cmd="java -jar target/KafkaConsumer-1.0-SNAPSHOT.jar $broker_host_port $consumer_group_id $topic $sleep $append_overwrite $fout"
echo "$cmd"
eval "$cmd"
