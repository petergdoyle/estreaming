/*
 */
package com.cleverfishsoftware.estreaming.jms.consumer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/*
         *
         * @author peter
 */
public class RunnableJMSConsumer implements Runnable {

    private final ConnectionFactory cf;
    private final String queueName;
    private final Connection connection;
    private final Session session;
    private final Queue queue;
    private final Destination destination;
    private final MessageConsumer consumer;
    private final long sleep;
    private final boolean noisy;
    private final String identifier;
    
    private final AtomicInteger count = new AtomicInteger();
    private boolean running = true;

    public RunnableJMSConsumer(String identifier, ConnectionFactory cf, String queueName, long sleep, boolean noisy) throws JMSException {
        this.cf = cf;
        this.queueName = queueName;
        connection = cf.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(queueName);
        // Create the destination (Topic or Queue)
        destination = session.createQueue(queueName);
        // Create a MessageConsumer from the Session to the Topic or Queue
        consumer = session.createConsumer(destination);
        this.sleep = sleep;
        this.identifier = identifier;
        this.noisy=noisy;
    }

    @Override
    public void run() {

        try {
            while (running) {

                try {
//                    System.out.println("waiting...");
                    // A JMS message consumer can consume messages synchronously by calling one of the MessageConsumer's receive() methods:
                    //receive()
                    //receive (long timeout)
                    //receiveNoWait();
                    //Calling receive() or receive (0) will block until there is a message available. This is the easiest form to receive a message synchronously. 
                    //The only disadvantage is that the application thread is blocked indefinitely.
                    //
                    //Receive with timeout and receiveNoWait are the two alternative API to consume messages synchronously. But applications should be 
                    //aware of the intended contract and behavior of the APIs. Applications may not function reliably or consistently if they are not used properly.
                    //                1. receive (long timeout)
                    //
                    //The JMS API JavaDoc for the API is quoted as follows.
                    //
                    //"Receives the next message that arrives within the specified timeout interval.
                    //
                    //This call blocks until a message arrives, the timeout expires, or this message consumer is closed. A timeout of zero never expires, and 
                    //the call blocks indefinitely."
                    //
                    //One important thing to note is the timeout value. This is the maximum wait time that the application should be expecting to wait. The call 
                    //returns immediately if there is a message available. The call may block up to the specified timeout value if no message is available.

                    // Wait for a message
                    Message message = consumer.receive(0);

                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    if (noisy) {
                        System.out.println(identifier + " received: " + count.incrementAndGet() + " messages" + "  msg: " + text);
                    } else {
                        System.out.print("\r" + count.incrementAndGet() + " received");
                    }
                    if (sleep > 0) {
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException ex) {
                        }
                    }

                } catch (JMSException ex) {
                    Logger.getLogger(RunnableJMSConsumer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            consumer.close();
            session.close();
            connection.close();

        } catch (JMSException ex) {
            Logger.getLogger(RunnableJMSConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void shutdown() {
        running = false;
    }

}
