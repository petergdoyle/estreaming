/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms;

import com.cleverfishsoftware.loadgenerator.MessageSender;
import com.cleverfishsoftware.loadgenerator.MessageSenderBuilder;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import static com.cleverfishsoftware.loadgenerator.Common.NullOrEmpty;

/**
 *
 * @author peter
 */
public class JMSMessageSenderBuilder implements MessageSenderBuilder {

    @Override
    public MessageSender getInstance(Properties props) throws Exception {

        String cfpClassName = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA);
        if (NullOrEmpty(cfpClassName)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA);
        }
        Class<ConnectionFactoryProvider> cfpClass = (Class<ConnectionFactoryProvider>) Class.forName(cfpClassName);
        ConnectionFactoryProvider cfp = cfpClass.newInstance();

        String queueName = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE);
        if (NullOrEmpty(queueName)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE);
        }

        ConnectionFactory cf = cfp.getInstance(props);

        return new JMSMessageSender(cf, queueName);

    }
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERCLA = "LoadGenerator.ConnectionFactoryProvider.class";
    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERQUE = "LoadGenerator.ConnectionFactoryProvider.queue_name";

}
