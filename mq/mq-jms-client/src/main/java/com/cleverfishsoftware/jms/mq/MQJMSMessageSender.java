package com.cleverfishsoftware.jms.mq;

import javax.jms.DeliveryMode;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.JMSException;

/**
 *
 * @author peter
 */
public class MQJMSMessageSender {

    public static void main(String[] args) throws JMSException {

        final String hostName = setHostName(args);
        final int port = setPort(args);
        final String queueManagerName = setQueueManagerName(args);
        final String channelName = setChannelName(args);
        final String queueName = setQueueName(args);
        final String msg = setMsg(args);
        final boolean securityEnabled = setSecurityEnabled(args);

        MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
        cf.setHostName(hostName);
        cf.setPort(port);
        cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        cf.setQueueManager(queueManagerName);
        cf.setChannel(channelName);

        if (securityEnabled) {
            System.out.println("security enabled. checking name and password.");
            String user = setUser(args);
            String password = setPassword(args);
            applySecurity(cf, user, password);
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
        } /*else {
        cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, false);
        }*/

        try (QueueConnection queueConn = cf.createQueueConnection()) {
            QueueSession queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender queueSender = queueSession.createSender(queueSession.createQueue(queueName));
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage message = queueSession.createTextMessage(msg);
            queueSender.send(message);
            System.out.println("successfully sent 1 message to the queue(" + queueName + ")");
        }

    }

    private static String setPassword(String[] args) {
        final String password = args.length != 0 && args[8] != null && args[8].length() > 0 ? args[8] : "passw0rd";
        return password;
    }

    private static String setUser(String[] args) {
        final String user = args.length != 0 && args[7] != null && args[7].length() > 0 ? args[7] : "vagrant";
        return user;
    }

    private static boolean setSecurityEnabled(String[] args) {
        final boolean securityEnabled = args.length != 0 && args[6] != null && args[6].length() > 0 && args[6].equalsIgnoreCase("true");
        return securityEnabled;
    }

    private static String setMsg(String[] args) {
        final String msg = args.length != 0 && args[5] != null && args[5].length() > 0 ? args[5] : "01010101010101";
        return msg;
    }

    private static String setQueueName(String[] args) {
        final String queueName = args.length != 0 && args[4] != null && args[4].length() > 0 ? args[4] : "QUEUE1";
        return queueName;
    }

    private static String setChannelName(String[] args) {
        final String channelName = args.length != 0 && args[3] != null && args[3].length() > 0 ? args[3] : "SYSTEM.DEF.SVRCONN";
        return channelName;
    }

    private static String setQueueManagerName(String[] args) {
        final String queueManagerName = args.length != 0 && args[2] != null && args[2].length() > 0 ? args[2] : "QM1";
        return queueManagerName;
    }

    private static int setPort(String[] args) {
        final int port = args.length != 0 && args[1] != null && args[1].length() > 0 ? Integer.parseInt(args[1]) : 1414;
        return port;
    }

    private static String setHostName(String[] args) {
        final String hostName = args.length != 0 && args[0] != null && args[0].length() > 0 ? args[0] : "localhost";
        return hostName;
    }

    private static void applySecurity(final MQQueueConnectionFactory cf, final String user, final String password) throws JMSException {
        cf.setStringProperty(WMQConstants.USERID, user);
        cf.setStringProperty(WMQConstants.PASSWORD, password);
//        QueueConnection queueConn = cf.createQueueConnection(user, password);
    }
}
