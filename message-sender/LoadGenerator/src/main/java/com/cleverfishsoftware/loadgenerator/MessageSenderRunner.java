/*
 */
package com.cleverfishsoftware.loadgenerator;

import static com.cleverfishsoftware.loadgenerator.Common.isTrue;
import static com.cleverfishsoftware.loadgenerator.Common.notNull;
import com.google.common.util.concurrent.RateLimiter;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author peter
 */
public class MessageSenderRunner {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {

        Properties props = new Properties(); // any known properties for the PayloadGeneratorBuilder are found here... 
        System.getProperties().stringPropertyNames().stream().filter((key) -> (key.startsWith("LoadGenerator"))).forEach((key) -> {
            props.setProperty(key, System.getProperty(key));
        });

        String jmsMessageSenderBuilderClassName = args[0];
        String payloadGeneratorBuilderClassName = args[1];

        Class<PayloadGeneratorBuilder> payloadGeneratorBuilderClass = (Class<PayloadGeneratorBuilder>) Class.forName(payloadGeneratorBuilderClassName);
        PayloadGeneratorBuilder payloadGeneratorBuilder = payloadGeneratorBuilderClass.newInstance();
        PayloadGenerator payloadGenerator = payloadGeneratorBuilder.getInstance(props);

        Class<MessageSenderBuilder> messageSenderBuilderClassName = (Class<MessageSenderBuilder>) Class.forName(jmsMessageSenderBuilderClassName);
        MessageSenderBuilder messageSenderBuilder = messageSenderBuilderClassName.newInstance();
        MessageSender messageSender = messageSenderBuilder.getInstance(props);

        // apply suggested defaults first
        int rate = 1;
        int limit = 0;
        int messageSize = 100;
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int cores = availableProcessors;
        boolean noisy = false;

        // override defaults with supplied args
        String argValue = args[2];
        if (notNull(argValue)) {
            rate = Integer.parseInt(argValue);
        }
        argValue = args[3];
        if (notNull(argValue)) {
            limit = Integer.parseInt(argValue);
        }
        argValue = args[4];
        if (notNull(argValue)) {
            messageSize = Integer.parseInt(argValue);
        }
        argValue = args[5];
        if (notNull(argValue)) {
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
        argValue = args[6];
        if (isTrue(argValue)) {
            noisy = true;
        }

        System.out.println("Preparing to send "
                + (limit > 0 ? limit : " an unlimited number of ") + " (" + messageSize + " byte) messages "
                + "at a rate of  " + rate + " messages per second "
                + "using " + cores + " threads.");

        Date now = new Date(System.currentTimeMillis());

        final RateLimiter throttle = RateLimiter.create(rate);
        final ExecutorService executorService = Executors.newFixedThreadPool(cores);

        final AtomicInteger count = new AtomicInteger();

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
                        messageSender.send(payload);
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
