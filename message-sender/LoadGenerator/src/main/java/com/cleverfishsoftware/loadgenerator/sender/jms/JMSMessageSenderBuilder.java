/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms;

import static com.cleverfishsoftware.loadgenerator.Common.notNull;
import com.cleverfishsoftware.loadgenerator.MessageSender;
import com.cleverfishsoftware.loadgenerator.MessageSenderBuilder;
import java.util.Properties;
import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public class JMSMessageSenderBuilder implements MessageSenderBuilder {

    @Override
    public MessageSender getInstance(Properties props) throws Exception {

        String brokerUrl = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERBRO);
        if (!notNull(brokerUrl)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERBRO);
        }
        String queueName = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE);
        if (!notNull(brokerUrl)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE);
        }
        String cfpClassName = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA);
        if (!notNull(brokerUrl)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA);
        }
        Class<ConnectionFactoryProvider> cfpClass = (Class<ConnectionFactoryProvider>) Class.forName(cfpClassName);
        ConnectionFactoryProvider cfp = cfpClass.newInstance();

        ConnectionFactory cf = cfp.getInstance(brokerUrl, queueName);

        return new JMSMessageSender(cf, brokerUrl, queueName);

    }
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA = "LoadGenerator.ConnectionFactoryProvider.class";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE = "LoadGenerator.ConnectionFactoryProvider.queue_name";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERBRO = "LoadGenerator.ConnectionFactoryProvider.broker_url";

}
