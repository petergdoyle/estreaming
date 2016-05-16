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

        Class<ConnectionFactoryProvider> cfpClass = (Class<ConnectionFactoryProvider>) Class.forName(cfpClassName);
        ConnectionFactoryProvider cfp = cfpClass.newInstance();
        ConnectionFactory cf = cfp.getInstance(props);

        int numConsumers = 1;
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

        final List<RunnableJMSConsumer> consumers = new ArrayList<>();
        for (int i = 0; i < numConsumers; i++) {
            RunnableJMSConsumer consumer = new RunnableJMSConsumer(cf, queueName, 0);
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

    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA = "LoadGenerator.ConnectionFactoryProvider.class";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE = "LoadGenerator.ConnectionFactoryProvider.queue_name";
}
