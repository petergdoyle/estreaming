/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

/**
 *
 * @author peter
 */
public class AbstractSender {

    protected final String brokerUrl;
    protected final String queueName;

    public AbstractSender(String brokerUrl, String queueName) {
        this.brokerUrl = brokerUrl;
        this.queueName = queueName;
    }
}
