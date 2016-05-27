#!/bin/sh


read -e -p "Enter the proxy server host: " -i "localhost" kafka_rest_proxy_host
read -e -p "Enter the proxy server port number: " -i "8082" kafka_rest_proxy_port

while true; do
  echo -e "*** Select the Command to run ***
  1) List Topics
  2) Show Topic Details
  3) Show Partition Details
  "
  read opt
  case $opt in
      1)
      cmd='curl "'http://localhost:8082/topics'"'
      break
      ;;

      2)
      read -e -p "Enter the topic name: " -i "use-case-1" topic
      cmd='curl "'http://localhost:8082/topics/$topic'"'
      break
      ;;

      3)
      read -e -p "Enter the topic name: " -i "use-case-1" topic
      cmd='curl "'http://localhost:8082/topics/$topic/partitions'"'
      break
      ;;

      *)
      echo "That option is not currently supported, try again."
      ;;
  esac
done
echo $cmd
eval $cmd
echo -e "\n"


# Produce a message using binary embedded data with value "Kafka" to the topic binarytest
curl -X POST -H "Content-Type: application/vnd.kafka.binary.v1+json" \
      --data '{"records":[{"value":"S2Fma2E="}]}' "http://localhost:8082/topics/binarytest" \
  {"offsets":[{"partition":0,"offset":0,"error_code":null,"error":null}],"key_schema_id":null,"value_schema_id":null}


# Create a consumer for binary data, starting at the beginning of the topic's
# log. Then consume some data from a topic using the base URL in the first response.
# Finally, close the consumer with a DELETE to make it leave the group and clean up
# its resources.

curl -X POST -H "Content-Type: application/vnd.kafka.v1+json" \
      --data '{"name": "my_consumer_instance", "format": "binary", "auto.offset.reset": "smallest"}' \
      http://localhost:8082/consumers/my_binary_consumer \
  {"instance_id":"my_consumer_instance","base_uri":"http://localhost:8082/consumers/my_binary_consumer/instances/my_consumer_instance"}

  curl -X GET -H "Accept: application/vnd.kafka.binary.v1+json" \
      http://localhost:8082/consumers/my_binary_consumer/instances/my_consumer_instance/topics/binarytest

curl -X GET -H "Accept: application/vnd.kafka.binary.v1+json" \
      http://localhost:8082/consumers/my_binary_consumer/instances/my_consumer_instance/topics/binarytest
