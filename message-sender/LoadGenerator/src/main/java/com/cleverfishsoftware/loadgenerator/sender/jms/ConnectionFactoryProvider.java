/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms;

import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public interface ConnectionFactoryProvider {

    ConnectionFactory getInstance(String brokerUrl, String queueName);

}
