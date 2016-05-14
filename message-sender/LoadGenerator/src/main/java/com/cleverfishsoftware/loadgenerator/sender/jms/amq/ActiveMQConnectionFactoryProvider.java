/*

 */
package com.cleverfishsoftware.loadgenerator.sender.jms.amq;

import com.cleverfishsoftware.loadgenerator.sender.jms.ConnectionFactoryProvider;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import static com.cleverfishsoftware.loadgenerator.Common.NullOrEmpty;

/**
 *
 * @author peter
 */
public class ActiveMQConnectionFactoryProvider implements ConnectionFactoryProvider {

    @Override
    public ConnectionFactory getInstance(Properties props) {
        
        String brokerUrl = props.getProperty(LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERBRO);
        if (NullOrEmpty(brokerUrl)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERBRO);
        }
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        ((ActiveMQConnectionFactory) cf).setUseAsyncSend(true);
        PooledConnectionFactory pcf = new PooledConnectionFactory();
        pcf.setConnectionFactory(cf);
        return pcf;
    }

    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERBRO = "LoadGenerator.ConnectionFactoryProvider.broker_url";

}
