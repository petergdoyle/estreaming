/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import static com.cleverfishsoftware.spring.xd.jms.sender.Broker.DEFAULT_BROKER_URL;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author peter.doyle
 */
public class TestMessageSender {

    public static void main(String... args) throws InterruptedException {

        ConnectionFactory cf = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);

        MessageSender
                .create(
                        cf,
                        "default",
                        1000l / 1,
                        true
                )
                .start();
    }

}
