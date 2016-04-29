/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms;

import com.cleverfishsoftware.loadgenerator.MessageSender;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 *
 * @author peter
 */
public class JmsApiMessageSender extends AbstractJMSSender implements MessageSender {

    static private JmsApiMessageSender instance;

    private final Connection connection;

    private final Session session;
    private final Queue queue;
    private final MessageProducer producer;

    public static MessageSender getInstance(ConnectionFactory cf, String brokerUrl, String queueName) throws Exception {
        instance = new JmsApiMessageSender(cf, brokerUrl, queueName);
        return instance;
    }

    private JmsApiMessageSender(ConnectionFactory cf, String brokerUrl, String queueName) throws JMSException {
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
