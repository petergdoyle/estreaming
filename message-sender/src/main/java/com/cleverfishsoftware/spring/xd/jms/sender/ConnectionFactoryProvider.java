/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public interface ConnectionFactoryProvider {

    ConnectionFactory getInstance(String brokerUrl, String queueName);

}
