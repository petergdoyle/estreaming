/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.sample;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 *
 * @author peter
 */
public class JmsMessageSender extends AbstractSender implements MessageSender {

    private static JmsMessageSender instance;
    private final Connection connection;
    private final Session session;
    private final Queue queue;
    private final MessageProducer producer;

    public static MessageSender getInstance(ConnectionFactory cf, String brokerUrl, String queueName) throws Exception {
        instance = new JmsMessageSender(cf, brokerUrl, queueName);
        return instance;
    }

    JmsMessageSender(ConnectionFactory cf, String brokerUrl, String queueName) throws Exception {
        super(brokerUrl, queueName);
        connection = cf.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(queueName);
        producer = session.createProducer(queue);
    }

    @Override
    public void send(String payload) throws Exception {
        Message msg = session.createTextMessage(payload);
        producer.send(msg);
    }

}
