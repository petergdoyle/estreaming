/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.jms;

import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public interface ConnectionFactoryProvider {

    ConnectionFactory getInstance(String brokerUrl, String queueName);

}
