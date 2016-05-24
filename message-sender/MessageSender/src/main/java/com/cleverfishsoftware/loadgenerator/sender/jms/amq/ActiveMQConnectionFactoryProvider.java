/*

 */
package com.cleverfishsoftware.loadgenerator.sender.jms.amq;

import com.cleverfishsoftware.loadgenerator.sender.jms.ConnectionFactoryProvider;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import static com.cleverfishsoftware.loadgenerator.Common.NullOrEmpty;
import org.apache.activemq.ActiveMQPrefetchPolicy;

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
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        cf.setUseAsyncSend(true);
        ActiveMQPrefetchPolicy prefetchPolicy=new ActiveMQPrefetchPolicy();
        prefetchPolicy.setQueuePrefetch(1);
        cf.setPrefetchPolicy(prefetchPolicy);
        PooledConnectionFactory pcf = new PooledConnectionFactory();
        pcf.setConnectionFactory(cf);
        return pcf;
    }

    private static final String LOAD_GENERATOR_CONNECTION_FACTORY_PROVIDERBRO = "LoadGenerator.ConnectionFactoryProvider.broker_url";

}
