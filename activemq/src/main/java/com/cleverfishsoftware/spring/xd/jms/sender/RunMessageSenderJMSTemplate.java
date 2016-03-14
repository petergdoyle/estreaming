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
public class RunMessageSenderJMSTemplate {

    public static void main(String[] args) throws Exception {

        String brokerUrl = args[0];
        int tps = Integer.parseInt(args[1]);
        String queueName = args[2];

        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        int sleep = 0;
        if (tps > 0) {
            sleep = (int) (1000l / tps);
        }

        MessageSenderJMSTemplate.create(
                brokerUrl,
                cf,
                queueName,
                sleep,
                false)
                .start();

    }

}
