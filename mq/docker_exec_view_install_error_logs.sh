
read -e -p "How do you want to view (less|tailf): " -i "less" response
if [ "$response" == "less" ]; then
  cmd="less"
else
  cmd="tail -f"
fi

docker exec -ti estreaming_ibm_mq8_broker $cmd /var/mqm/errors/AMQERR01.LOG
