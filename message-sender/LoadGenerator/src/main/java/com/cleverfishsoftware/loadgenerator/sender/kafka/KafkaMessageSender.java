/*
 */
package com.cleverfishsoftware.loadgenerator.sender.kafka;

import com.cleverfishsoftware.loadgenerator.MessageSender;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author peter
 */
public class KafkaMessageSender implements MessageSender {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    KafkaMessageSender(Properties kafkaProperties, String topic) {
        producer = new KafkaProducer<>(kafkaProperties);
        this.topic = topic;
    }

    @Override
    public void send(String payload) throws Exception {
        producer.send(new ProducerRecord<>(topic, payload));
    }

}
