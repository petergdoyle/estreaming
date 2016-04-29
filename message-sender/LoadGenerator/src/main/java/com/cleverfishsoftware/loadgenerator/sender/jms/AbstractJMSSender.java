/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms;

/**
 *
 * @author peter
 */
public class AbstractJMSSender {

    protected final String brokerUrl;
    protected final String queueName;

    public AbstractJMSSender(String brokerUrl, String queueName) {
        this.brokerUrl = brokerUrl;
        this.queueName = queueName;
    }
}
