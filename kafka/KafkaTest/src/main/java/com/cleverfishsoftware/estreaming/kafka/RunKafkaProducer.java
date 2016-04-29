/*
 */
package com.cleverfishsoftware.estreaming.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author peter
 */
public class RunKafkaProducer {

    public static void main(String[] args) throws IOException {
        String hostPort = args[0];
        String topic = args[1];
        String msg = args[2];

        // set up the producer
        KafkaProducer<String, String> producer;
        
        try (InputStream props = RunKafkaProducer.class.getClassLoader().getResourceAsStream("producer.properties")) {
            Properties properties = new Properties();
            properties.load(props);
            properties.setProperty("bootstrap.servers", hostPort);
            producer = new KafkaProducer<>(properties);
        }

        try {
            producer.send(new ProducerRecord<>(topic, msg));
        } finally {
            producer.close();
        }
    }
}
