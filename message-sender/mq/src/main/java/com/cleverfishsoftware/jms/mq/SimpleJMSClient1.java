/*
 */
package com.cleverfishsoftware.jms.mq;

/**
 *
 * @author peter
 */
import javax.jms.DeliveryMode;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

/**
 */
public class SimpleJMSClient1 {

    public void sendMsg(String msg) {
        MQQueueConnectionFactory cf = null;
        QueueConnection queueConn = null;
        QueueSession queueSession = null;
        QueueSender queueSender = null;
        TextMessage message = null;

        try {

            cf = new MQQueueConnectionFactory();
            cf.setHostName("localhost");
            cf.setPort(new Integer("1414"));
            cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            cf.setQueueManager("QM1");
            cf.setChannel("SYSTEM.DEF.SVRCONN");
            user = "vagrant";
            password = "passw0rd";
            cf.setStringProperty(WMQConstants.USERID, user);
            cf.setStringProperty(WMQConstants.PASSWORD, password);
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
//            queueConn = cf.createQueueConnection(user, password);
            queueConn = cf.createQueueConnection();
            queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(queueSession.createQueue("QName"));
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            message = queueSession.createTextMessage(msg);
            queueSender.send(message);
            queueSender.send(message);
            queueConn.close();

        } catch (Exception je) {
            je.printStackTrace();
        }
    }
    private String password;
    private String user;

    public static void main(String[] args) {
        new SimpleJMSClient1().sendMsg("Hi Kausik ... How are you ?");
    }
}
