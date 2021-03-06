/*
 */
package com.cleverfishsoftware.estreaming.amq.broker;

import static com.cleverfishsoftware.estreaming.amq.broker.Broker.*;

/**
 *
 * @author peter.doyle
 */
public class RunBroker {

    public static void main(String... args) {

        try {
            Broker.create(
                    DEFAULT_BROKER_NAME,
                    DEFAULT_BROKER_URL,
                    DEFAULT_STORE_USAGE,
                    DEFAULT_TMP_USAGE)
                    .start();
        } catch (Exception ex) {
            System.out.println("Could not start broker: " + ex.getMessage());
        }
    }
}
