Add Kafka and Storm to Aggregate the Stream!
============================================


Start the Zookeeper cluster for Kafka
```console
[vagrant@estreaming ~] /home/vagrant/kafka/default/bin/zookeeper-server-start.sh /home/vagrant/kafka/default/config/zookeeper.properties
```

Start the single-node instance of Kafka to run under that Zookeeper
```console
[vagrant@estreaming ~] /home/vagrant/kafka/default/bin/zookeeper-server-start.sh /home/vagrant/kafka/default/config/zookeeper.properties
```

Create a Spring XD Tap to intercept formatted Results from the existing airshop_stream. This also creates a topic for Kafka consumers
```console
xd:>stream create --name tap_airshop_stream_to_kafka --definition "tap:stream:airshop_stream.transform > kafka --topic=airshopTopic" --deploy
```

Let's do a console kafka consumer to tail these airshop_stream results
```console
[vagrant@estreaming ~] /home/vagrant/kafka/default/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic airshopTopic --from-beginning
```
