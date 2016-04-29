/*
 */
package com.cleverfishsoftware.loadgenerator;

import com.cleverfishsoftware.loadgenerator.sender.jms.JmsMessageSenderBuilder;
import com.cleverfishsoftware.loadgenerator.sender.jms.ConnectionFactoryProvider;
import com.google.common.util.concurrent.RateLimiter;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public class MessageSenderRunner {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {

        String brokerUrl = args[0];
        String queueName = args[1];
        String connectionFactoryProviderClassName = args[2];
        String payloadGeneratorBuilderClassName = args[3];
//        String jmsMessageSenderBuilderClassName = args[4];

        Properties props = new Properties(); // any known properties for the PayloadGeneratorBuilder are found here... 
        if (System.getProperties().containsKey("FileSystemPayloadGenerator.file")) {
            props.setProperty("FileSystemPayloadGenerator.file", System.getProperty("FileSystemPayloadGenerator.file"));
        }

        int rate = 1;
        int limit = 0;
        int messageSize = 100;
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int cores = availableProcessors;
        boolean noisy = false;

        Class<ConnectionFactoryProvider> connectionFactoryProviderClass = (Class<ConnectionFactoryProvider>) Class.forName(connectionFactoryProviderClassName);
        ConnectionFactoryProvider connectionFactoryProvider = connectionFactoryProviderClass.newInstance();

        Class<PayloadGeneratorBuilder> payloadGeneratorBuilderClass = (Class<PayloadGeneratorBuilder>) Class.forName(payloadGeneratorBuilderClassName);
        PayloadGeneratorBuilder  payloadGeneratorBuilder = payloadGeneratorBuilderClass.newInstance();
        PayloadGenerator payloadGenerator = payloadGeneratorBuilder.getInstance(props);

//        Class<MessageSenderBuilder> messageSenderClass = (Class<MessageSenderBuilder>)Class.forName(jmsMessageSenderBuilderClassName);

        if (args.length > 4) {
            String argValue = args[4];
            if (argValue != null && argValue.length() > 0) {
                rate = Integer.parseInt(argValue);
            }
        }
        if (args.length > 5) {
            String argValue = args[5];
            if (argValue != null && argValue.length() > 0) {
                limit = Integer.parseInt(argValue);
            }
        }
        if (args.length > 6) {
            String argValue = args[6];
            if (argValue != null && argValue.length() > 0) {
                messageSize = Integer.parseInt(argValue);
            }
        }
        if (args.length > 7) {
            String argValue = args[7];
            if (argValue != null && argValue.length() > 0) {
                cores = Integer.parseInt(argValue);
                if (cores % availableProcessors != 0) {
                    if (cores <= availableProcessors) {
                        cores = availableProcessors;
                    } else {
                        while (cores % availableProcessors != 0) {
                            cores--;
                        }
                    }
                }
            }
        }
        if (args.length > 8) {
            String argValue = args[8];
            if (argValue != null && argValue.length() > 0
                    && (argValue.toLowerCase().equals("y")
                    || argValue.toLowerCase().equals("yes")
                    || argValue.toLowerCase().equals("true"))) {
                noisy = true;
            }
        }

        System.out.println("Preparing to send "
                + (limit > 0 ? limit : " an unlimited number of ") + " (" + messageSize + " byte) messages "
                + "to "
                + brokerUrl + " "
                + "at a rate of  " + rate + " messages per second "
                + "using " + cores + " threads.");

        Date now = new Date(System.currentTimeMillis());

        final RateLimiter throttle = RateLimiter.create(rate);
        final ExecutorService executorService = Executors.newFixedThreadPool(cores);

        final AtomicInteger count = new AtomicInteger();
        final ConnectionFactory cf = connectionFactoryProvider.getInstance(brokerUrl, queueName);
        final MessageSender sender = JmsMessageSenderBuilder.getInstance(cf, brokerUrl, queueName);

        while (true) {
            if (limit > 0 && count.get() == limit) {
                break;
            }
            final String[] payloads = payloadGenerator.getPayload(messageSize);
            for (final String payload : payloads) {
                if (noisy) {
                    System.out.println(payload);
                }
                throttle.acquire();
                if (limit > 0 && count.get() == limit) {
                    break;
                }
                count.incrementAndGet();
                executorService.submit(() -> {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    try {
                        sender.send(payload);
                        System.out.print("\r" + count.get() + " sent");
                    } catch (Exception ex) {
                    }
                });
            }
        }
        Date then = new Date(System.currentTimeMillis());

        printDifference(now, then);

        executorService.shutdown();

        System.exit(0);
    }

    public static void printDifference(Date startDate, Date endDate) {

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "\nelapsed time: %d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

    }

}
