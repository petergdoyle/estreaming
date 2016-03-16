/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.sample;

import com.google.common.util.concurrent.RateLimiter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public class RunMessageSender {

    public final static String CHUNK = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {

        String brokerUrl = args[0];
        String queueName = args[1];
        String connectionFactoryProviderClassName = args[2];
        int rate = 1;
        int limit = 0;
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int cores = availableProcessors;
        boolean noisy = false;

        Class<ConnectionFactoryProvider> connectionFactoryProviderClass = (Class<ConnectionFactoryProvider>) Class.forName(connectionFactoryProviderClassName);
        ConnectionFactoryProvider connectionFactoryProvider = connectionFactoryProviderClass.newInstance();

        if (args.length > 3) {
            String rateValue = args[3];
            if (rateValue != null && rateValue.length() > 0) {
                rate = Integer.parseInt(args[3]);
            }
        }
        if (args.length > 4) {
            String rateValue = args[4];
            if (rateValue != null && rateValue.length() > 0) {
                limit = Integer.parseInt(args[4]);
            }
        }
        if (args.length > 5) {
            String coresValue = args[5];
            if (coresValue != null && coresValue.length() > 0) {
                cores = Integer.parseInt(args[5]);
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
        if (args.length > 6) {
            String noisyValue = args[6];
            if (noisyValue != null && noisyValue.length() > 0
                    && (noisyValue.toLowerCase().equals("y")
                    || noisyValue.toLowerCase().equals("yes")
                    || noisyValue.toLowerCase().equals("true"))) {
                noisy = true;
            }
        }

        final StringBuilder payloadBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            payloadBuilder.append(CHUNK);
        }
        final String payload = payloadBuilder.toString();

        System.out.println("Preparing to send "
                + (limit > 0 ? limit : "unlimited") + " (" + payload.length() + "byte) messages "
                + "to "
                + brokerUrl + " "
                + "at a rate of  " + rate + " messages per second "
                + "using " + cores + " threads.");

        Date now = new Date(System.currentTimeMillis());

        final RateLimiter throttle = RateLimiter.create(rate);
        final ExecutorService executorService = Executors.newFixedThreadPool(cores);

        final AtomicInteger count = new AtomicInteger();
        final ConnectionFactory cf = connectionFactoryProvider.getInstance(brokerUrl, queueName);
        final MessageSender sender = JmsMessageSender.getInstance(cf, brokerUrl, queueName);

        while (true) {
            throttle.acquire();
            if (limit > 0 && count.get() == limit) {
                break;
            }
            count.incrementAndGet();
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    try {
                        sender.send(payload);
                        System.out.print("\r" + count.get() + " sent");
                    } catch (Exception ex) {
                    }
                }

            });
        }
        System.out.print("\n");
        Date then = new Date(System.currentTimeMillis());
        printDifference(now, then);
        executorService.shutdown();
        System.exit(0);
//        if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
//            System.out.println("Exiting now...");
//            System.exit(0);
//        }
    }

    public static void printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

//        System.out.println("startDate : " + startDate);
//        System.out.println("endDate : " + endDate);
//        System.out.println("different : " + different);
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
                "elapsed time: %d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

    }

}
