/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms.mq;

import com.cleverfishsoftware.loadgenerator.sender.jms.ConnectionFactoryProvider;
import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public class IBMMQConnectionFactoryProvider implements ConnectionFactoryProvider {

    @Override
    public ConnectionFactory getInstance(String brokerUrl, String queueName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
