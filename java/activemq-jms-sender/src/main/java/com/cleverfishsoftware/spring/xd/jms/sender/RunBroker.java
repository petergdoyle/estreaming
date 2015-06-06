/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import static com.cleverfishsoftware.spring.xd.jms.sender.Broker.DEFAULT_BROKER_URL;
import static org.apache.activemq.broker.BrokerService.DEFAULT_BROKER_NAME;

/**
 *
 * @author peter.doyle
 */
public class RunBroker {

    public static void main(String... args) {

        try {
            Broker.create(
                    DEFAULT_BROKER_NAME,
                    DEFAULT_BROKER_URL)
                    .start();
        } catch (Exception ex) {
            System.out.println("Could not start broker: " + ex.getMessage());
        }
    }
}
