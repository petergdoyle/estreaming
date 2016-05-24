/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms.mq;

import com.cleverfishsoftware.loadgenerator.MessageSender;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author peter
 */
public class IBMMQMessageSender implements MessageSender {
    
    private final MQQueueConnectionFactory cf;
    private final QueueSession queueSession;
    private final QueueSender queueSender;
    private final QueueConnection queueConn;
    
    public IBMMQMessageSender(MQQueueConnectionFactory cf, String queueName) throws JMSException {
        this.cf = cf;
        System.out.println("DEBUG " + IBMMQMessageSender.class.getName() + " host:" + this.cf.getHostName() + " port:" + cf.getPort());
        queueConn = cf.createQueueConnection();
        queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        queueSender = queueSession.createSender(queueSession.createQueue(queueName));
        queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
    
    @Override
    public void send(String payload) throws Exception {
        TextMessage message = queueSession.createTextMessage(payload);
        queueSender.send(message);
    }
    
}
