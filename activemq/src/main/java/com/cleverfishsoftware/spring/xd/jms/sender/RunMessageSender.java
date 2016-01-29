/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import static com.cleverfishsoftware.spring.xd.jms.sender.Broker.DEFAULT_BROKER_URL;
import java.io.Console;
import java.util.Scanner;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author peter.doyle
 */
public class RunMessageSender {

    public static void main(String[] args) throws Exception {

        Console console = System.console();
        if (console == null) {
            System.out.println("this application requires a console.");
            System.exit(-1);
        }
        Scanner scanner = new Scanner(console.reader());

        int tps = 0;
        boolean tpsValid = false;
        while (!tpsValid) {
            console.printf("Please enter the desired message rate between 1 and 100 (requests per second):");
            tps = scanner.nextInt();
            if (tps > 0 & tps <= 100) {
                tpsValid = true;
            }
        }

        boolean queueNameValid = false;
        String queueName = null;
        while (!queueNameValid) {
            console.printf("Please enter a name for the JMS queue:");
            queueName = console.readLine();
            if (queueName != null && queueName.length() > 0) {
                queueNameValid = true;
            }
        }
        ConnectionFactory cf = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);

        MessageSender.create(
                cf,
                queueName,
                1000l / tps,
                true)
                .start();

    }

}
