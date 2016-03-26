/*
 */
package com.cleverfishsoftware.jms.mq;

import com.ibm.jms.JMSMessage;
import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

/**
 */
public class MqConnect {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            MQQueueConnectionFactory cf = new MQQueueConnectionFactory();

            // Config
            cf.setHostName("localhost");
            cf.setPort(1414);
            cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
            cf.setQueueManager("QM1");
            cf.setChannel("SYSTEM.DEF.SVRCONN");
            
            final String userId="vagrant";
            final String password="passw0rd";
            
            MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection(userId,password);
//            MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection(null,null);
            MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            MQQueue queue = (MQQueue) session.createQueue("queue:///Q1");
            MQQueueSender sender = (MQQueueSender) session.createSender((Queue) queue);
            MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver((Queue) queue);

            long uniqueNumber = System.currentTimeMillis() % 1000;
            JMSTextMessage message = (JMSTextMessage) session.createTextMessage("SimplePTP " + uniqueNumber);

            // Start the connection
            connection.start();

            sender.send(message);
            System.out.println("Sent message:\\n" + message);

            JMSMessage receivedMessage = (JMSMessage) receiver.receive(10000);
            System.out.println("\\nReceived message:\\n" + receivedMessage);

            sender.close();
            receiver.close();
            session.close();
            connection.close();

            System.out.println("\\nSUCCESS\\n");
        } catch (JMSException jmsex) {
            System.out.println(jmsex);
            System.out.println("\\nFAILURE\\n");
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("\\nFAILURE\\n");
        }
    }
}
