/*
 */
package com.cleverfishsoftware.estreaming.activemq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.jms.JMSException;

/**
 *
 * @author peter
 */
public class RunAMQConsumer {

    public static void main(String[] args) throws JMSException {

        String brokerUrl = args[0];
        String queueName = args[1];
        long sleep = Long.parseLong(args[2]);

        int numConsumers = 1;
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

        final List<RunnableAMQConsumer> consumers = new ArrayList<>();
        for (int i = 0; i < numConsumers; i++) {
            RunnableAMQConsumer consumer = new RunnableAMQConsumer(brokerUrl, queueName, sleep);
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
