/*
 */
package com.cleverfishsoftware.estreaming.kafka.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author peter
 */
public class RunKafkaConsumer {

    public static void main(String[] args) {

        String bootstrapServers = args[0];
        String consumerGroup = args[1];
        List<String> topics = Arrays.asList(args[2].split(","));
        long sleep = Long.parseLong(args[3]);

        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", bootstrapServers);
        kafkaProperties.put("group.id", consumerGroup);
        kafkaProperties.put("enable.auto.commit", "true");
        kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put("session.timeout.ms", "10000");
        kafkaProperties.put("fetch.min.bytes", "50000");
        kafkaProperties.put("receive.buffer.bytes", "262144");
        kafkaProperties.put("max.partition.fetch.bytes", "2097152");

        int numConsumers = 1;
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

        final List<RunnableKafkaConsumer> consumers = new ArrayList<>();
        for (int i = 0; i < numConsumers; i++) {
            RunnableKafkaConsumer consumer = new RunnableKafkaConsumer(i, kafkaProperties, topics, sleep);
            consumers.add(consumer);
            executor.submit(consumer);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                consumers.stream().forEach((consumer) -> {
                    consumer.shutdown();
                });
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                }
            }
        });

    }

}
