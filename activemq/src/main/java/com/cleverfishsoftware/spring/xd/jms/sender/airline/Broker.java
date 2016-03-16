/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.airline;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.usage.SystemUsage;

/**
 *
 * @author peter.doyle
 */
public class Broker {

    public static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
    public static final String DEFAULT_BROKER_NAME = "default";
    private static final int MB = 1024 * 1024;
    public static final int DEFAULT_STORE_USAGE = MB * 64;
    public static final int DEFAULT_TMP_USAGE = MB * 64;

    public static Broker create(String brokerName, String brokerUrl, int storeUsage, int tempUsage) {
        return new Broker(brokerName, brokerUrl, storeUsage, tempUsage);
    }

    private final String brokerUrl;
    private final String brokerName;
    private final int tempUsage;
    private final int storeUsage;

    private Broker(String brokerName, String brokerUrl, int storeUsage, int tempUsage) {
        this.brokerName = (brokerName != null && brokerName.length() > 0) ? brokerName : DEFAULT_BROKER_NAME;
        this.brokerUrl = (brokerUrl != null && brokerUrl.length() > 0) ? brokerUrl : DEFAULT_BROKER_URL;
        this.storeUsage = storeUsage > 0 ? storeUsage : DEFAULT_STORE_USAGE;
        this.tempUsage = tempUsage > 0 ? tempUsage : DEFAULT_TMP_USAGE;
    }

    public void start() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName(brokerName);
        broker.addConnector(brokerUrl);

        SystemUsage systemUsage = broker.getSystemUsage();
        systemUsage.getStoreUsage().setLimit(storeUsage);
        systemUsage.getTempUsage().setLimit(tempUsage);

        broker.setPersistent(false);

        broker.start();

        System.out.println("Started ActiveMQ broker: " + broker.toString());
    }

}
