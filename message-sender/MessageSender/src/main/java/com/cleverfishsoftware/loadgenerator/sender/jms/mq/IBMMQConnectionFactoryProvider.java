/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms.mq;

import com.cleverfishsoftware.loadgenerator.sender.jms.ConnectionFactoryProvider;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import static com.cleverfishsoftware.loadgenerator.Common.NullOrEmpty;

/**
 *
 * @author peter
 */
public class IBMMQConnectionFactoryProvider implements ConnectionFactoryProvider {

    @Override
    public ConnectionFactory getInstance(Properties props) throws Exception {

        String mqHost = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_HOST);
        if (NullOrEmpty(mqHost)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_HOST);
        }

        String mqPortValue = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_PORT);

        int mqPort = 0;
        if (NullOrEmpty(mqPortValue)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_PORT);
        }
        mqPort = Integer.parseInt(mqPortValue);
        System.out.println("DEBUG " + IBMMQConnectionFactoryProvider.class.getName() + " host:" + mqHost + " port:" + mqPort);

        String mqQueueManager = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_QM);
        if (NullOrEmpty(mqQueueManager)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_QM);
        }

        String mqChannel = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_CH);
        if (NullOrEmpty(mqChannel)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_CH);
        }

        String mqQueue = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_QN);
        if (NullOrEmpty(mqQueue)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_QN);
        }

        MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
        cf.setHostName(mqHost);
        cf.setPort(mqPort);
        cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        cf.setQueueManager(mqQueueManager);
        cf.setChannel(mqChannel);
        
//        https://www.ibm.com/developerworks/community/blogs/messaging/entry/asynchronous_message_send_using_mq_jms_2_0?lang=en
//        JMSContext jmsContext = cf.createContext();
//        JMSProducer jmsProducer = jmsContext.createProducer();
//        jmsProducer.setAsync(new CompletionListener() {
//            @Override
//            public void onCompletion(Message msg) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void onException(Message msg, Exception excptn) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
        

        boolean securityEnabledChannel = true;

        if (securityEnabledChannel) {

            String mqUser = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_US);
            if (NullOrEmpty(mqUser)) {
                throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_US);
            }

            String mqPw = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_PW);
            if (NullOrEmpty(mqPw)) {
                throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_PW);
            }

            cf.setStringProperty(WMQConstants.USERID, mqUser);
            cf.setStringProperty(WMQConstants.PASSWORD, mqPw);
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
        }

        return cf;

    }

    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_PW = "LoadGenerator.ConnectionFactoryProvider.mq_pw";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_US = "LoadGenerator.ConnectionFactoryProvider.mq_user";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_QN = "LoadGenerator.ConnectionFactoryProvider.queue_name";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_CH = "LoadGenerator.ConnectionFactoryProvider.mq_channel";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_QM = "LoadGenerator.ConnectionFactoryProvider.mq_queue_manager";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_PORT = "LoadGenerator.ConnectionFactoryProvider.mq_port";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERMQ_HOST = "LoadGenerator.ConnectionFactoryProvider.mq_host";

}
