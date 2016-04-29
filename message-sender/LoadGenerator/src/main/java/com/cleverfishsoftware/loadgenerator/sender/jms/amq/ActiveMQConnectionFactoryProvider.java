/*

 */
package com.cleverfishsoftware.loadgenerator.sender.jms.amq;

import com.cleverfishsoftware.loadgenerator.sender.jms.ConnectionFactoryProvider;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

/**
 *
 * @author peter
 */
public class ActiveMQConnectionFactoryProvider implements ConnectionFactoryProvider {

    @Override
    public ConnectionFactory getInstance(String brokerUrl, String queueName) {
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        ((ActiveMQConnectionFactory) cf).setUseAsyncSend(true);
        PooledConnectionFactory pcf = new PooledConnectionFactory();
        pcf.setConnectionFactory(cf);
        return pcf;
    }

}
