/*
 */
package com.cleverfishsoftware.estreaming.jms.consumer;

import static com.cleverfishsoftware.loadgenerator.Common.NullOrEmpty;
import com.cleverfishsoftware.loadgenerator.sender.jms.ConnectionFactoryProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public class RunJMSConsumer {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties(); // any known properties for the RunJMSConsumer are found here... 
        System.getProperties().stringPropertyNames().stream().filter((key) -> (key.startsWith("RunJMSConsumer") || key.startsWith("LoadGenerator"))).forEach((key) -> {
            props.setProperty(key, System.getProperty(key));
        });

        String cfpClassName = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA);
        if (NullOrEmpty(cfpClassName)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA);
        }
        String queueName = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE);
        if (NullOrEmpty(queueName)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE);
        }

        String sleepTime = props.getProperty(RUN_JMS_CONSUMER_MESSAGE_CONSUMERSLEEP_TIME);
        if (NullOrEmpty(sleepTime)) {
            throw new RuntimeException("missing system property: " + RUN_JMS_CONSUMER_MESSAGE_CONSUMERSLEEP_TIME);
        }
        long sleep = Long.parseLong(sleepTime);

        String identifier = props.getProperty(RUN_JMS_CONSUMER_MESSAGE_CONSUMERCONSUMER_ID);
        if (NullOrEmpty(identifier)) {
            throw new RuntimeException("missing system property: " + RUN_JMS_CONSUMER_MESSAGE_CONSUMERCONSUMER_ID);
        }

        String noisyValue = props.getProperty(RUN_JMS_CONSUMER_MESSAGE_CONSUMERNOISY);
        if (NullOrEmpty(noisyValue)) {
            throw new RuntimeException("missing system property: " + RUN_JMS_CONSUMER_MESSAGE_CONSUMERNOISY);
        }
        boolean noisy = Boolean.parseBoolean(noisyValue);

        Class<ConnectionFactoryProvider> cfpClass = (Class<ConnectionFactoryProvider>) Class.forName(cfpClassName);
        ConnectionFactoryProvider cfp = cfpClass.newInstance();
        ConnectionFactory cf = cfp.getInstance(props);

        int numConsumers = 1;
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

        final List<RunnableJMSConsumer> consumers = new ArrayList<>();
        for (int i = 0; i < numConsumers; i++) {
            RunnableJMSConsumer consumer = new RunnableJMSConsumer(identifier, cf, queueName, sleep, noisy);
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
    private static final String RUN_JMS_CONSUMER_MESSAGE_CONSUMERNOISY = "RunJMSConsumer.MessageConsumer.noisy";
    private static final String RUN_JMS_CONSUMER_MESSAGE_CONSUMERCONSUMER_ID = "RunJMSConsumer.MessageConsumer.consumer_id";
    private static final String RUN_JMS_CONSUMER_MESSAGE_CONSUMERSLEEP_TIME = "RunJMSConsumer.MessageConsumer.sleep_time";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA = "LoadGenerator.ConnectionFactoryProvider.class";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE = "LoadGenerator.ConnectionFactoryProvider.queue_name";
}
