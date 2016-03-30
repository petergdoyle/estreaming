

read -e -p "Enter the Queue Manager name: " -i "QM1" qm

read -e -p "How do you want to view (less|tailf): " -i "tailf" response
if [ "$response" == "less" ]; then
  cmd="less"
else
  cmd="tail -f"
fi

docker exec -ti estreaming_ibm_mq8_broker $cmd /var/mqm/qmgrs/$qm/errors/AMQERR01.LOG
