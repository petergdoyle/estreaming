/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.sample;

import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

/**
 *
 * @author peter
 */
public class RunPureJMSMessageSender {

    public final static String CHUNK = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

    public static void main(String[] args) throws JMSException {

        String brokerUrl = args[0];
        String queueName = args[1];
        int tps = 0;
        boolean noisy = false;
        if (args.length > 2) {
            String tpsValue = args[2];
            if (tpsValue != null && tpsValue.length() > 0) {
                Integer.parseInt(args[2]);
            }
        }
        if (args.length > 3) {
            String noisyValue = args[3];
            if (noisyValue != null && noisyValue.length() > 0
                    && (noisyValue.toLowerCase().equals("y")
                    || noisyValue.toLowerCase().equals("yes")
                    || noisyValue.toLowerCase().equals("true"))) {
                noisy = true;
            }
        }

        ConnectionFactory cf = ConnectionFactoryProvider(brokerUrl);
        Connection connection = cf.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(queue);

        StringBuilder payload = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            payload.append(CHUNK);
        }

        AtomicInteger count = new AtomicInteger();

        while (true) {
            Message msg = session.createTextMessage(payload.toString());
            producer.send(msg);
            System.out.print("\r" + count.incrementAndGet() + " sent");
        }
    }

    private static ConnectionFactory ConnectionFactoryProvider(String brokerUrl) {
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        ((ActiveMQConnectionFactory) cf).setUseAsyncSend(true);
        PooledConnectionFactory pcf = new PooledConnectionFactory();
        pcf.setConnectionFactory(cf);
        return pcf;
    }
}
