/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import org.apache.activemq.broker.BrokerService;

/**
 *
 * @author peter.doyle
 */
public class Broker {

    public static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
    private static final String DEFAULT_BROKER_NAME = "broker";

    public static Broker create(String brokerName, String brokerUrl) {
        return new Broker(brokerName, brokerUrl);
    }

    private final String brokerUrl;
    private final String brokerName;

    private Broker(String brokerName, String brokerUrl) {
        this.brokerName = brokerName;
        this.brokerUrl = brokerUrl;
    }

    public void start() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName(brokerName);
        broker.addConnector(brokerUrl);
        broker.start();
        System.out.println("Started ActiveMQ broker: " + broker.toString());
    }

}
