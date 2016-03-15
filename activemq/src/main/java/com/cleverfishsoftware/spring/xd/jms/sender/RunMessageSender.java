/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

/**
 *
 * @author peter.doyle
 */
public class RunMessageSender {

    public static void main(String[] args) throws Exception {

        String brokerUrl = args[0];
        String queueName = args[1];
        int tps = Integer.parseInt(args[2]);
        String consoleOutput = args[3];
        boolean noisy = false;
        if (consoleOutput.toLowerCase().equals("y")
                || consoleOutput.toLowerCase().equals("yes")
                || consoleOutput.toLowerCase().equals("true")) {
            noisy = true;
        }


        MessageSender.create(
                brokerUrl,
                queueName,
                tps,
                noisy)
                .start();

    }

}
