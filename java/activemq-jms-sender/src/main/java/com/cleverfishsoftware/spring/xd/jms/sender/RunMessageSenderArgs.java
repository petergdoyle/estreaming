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
public class RunMessageSenderArgs {

    public static void main(String[] args) throws Exception {

        int tps = Integer.parseInt(args[0]);
        String queueName = args[1];

        ConnectionFactory cf = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);

        MessageSender.create(
                cf,
                queueName,
                1000l / tps,
                false)
                .start();

    }

}
