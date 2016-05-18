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
public class JMSMessageSender implements MessageSender {

    private final ConnectionFactory cf;
    private final String queueName;
    private final Connection connection;
    private final Session session;
    private final Queue queue;
    private final MessageProducer producer;

    public JMSMessageSender(ConnectionFactory cf, String queueName) throws JMSException {
        this.cf = cf;
        this.queueName = queueName;
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
