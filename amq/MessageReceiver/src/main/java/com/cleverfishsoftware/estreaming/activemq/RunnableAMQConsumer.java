/*
 */
package com.cleverfishsoftware.estreaming.activemq;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

/*
         *
         * @author peter
 */
public class RunnableAMQConsumer implements Runnable {

    private final PooledConnectionFactory pcf;
    private final Connection connection;
    private final Session session;
    private final Destination destination;
    private final MessageConsumer consumer;
    private final long sleep;
    private final AtomicInteger count = new AtomicInteger();
    private boolean running = true;

    public RunnableAMQConsumer(String brokerUrl, String queueName, long sleep) throws JMSException {
        // Create a ConnectionFactory

        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        ((ActiveMQConnectionFactory) cf).setUseAsyncSend(true);
        pcf = new PooledConnectionFactory();
        pcf.setConnectionFactory(cf);

        // Create a Connection
        connection = pcf.createConnection();
        connection.start();

        // Create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        destination = session.createQueue(queueName);

        // Create a MessageConsumer from the Session to the Topic or Queue
        consumer = session.createConsumer(destination);

        this.sleep = sleep;
    }

    @Override
    public void run() {

        try {
            while (running) {

                try {
                    // Wait for a message
                    // A JMS message consumer can consume messages synchronously by calling one of the MessageConsumer's receive() methods:
                    //receive()
                    //receive (long timeout)
                    //receiveNoWait();
                    //Calling receive() or receive (0) will block until there is a message available. This is the easiest form to receive a message synchronously. The only disadvantage is that the application thread is blocked indefinitely.
                    //
                    //Receive with timeout and receiveNoWait are the two alternative API to consume messages synchronously. But applications should be aware of the intended contract and behavior of the APIs. Applications may not function reliably or consistently if they are not used properly.
                    //                1. receive (long timeout)
                    //
                    //The JMS API JavaDoc for the API is quoted as follows.
                    //
                    //"Receives the next message that arrives within the specified timeout interval.
                    //
                    //This call blocks until a message arrives, the timeout expires, or this message consumer is closed. A timeout of zero never expires, and the call blocks indefinitely."
                    //
                    //One important thing to note is the timeout value. This is the maximum wait time that the application should be expecting to wait. The call returns immediately if there is a message available. The call may block up to the specified timeout value if no message is available.

                    Message message = consumer.receive(0);

                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String text = textMessage.getText();
                        System.out.println("Received: " + text + "    Processed: " + count.incrementAndGet() + " messages");
                    } else {
                        System.out.println("Received: " + message + "    Processed: " + count.incrementAndGet() + " messages");
                    }
                    if (sleep > 0) {
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException ex) {
                        }
                    }
                    System.out.println();

                } catch (JMSException ex) {
                    Logger.getLogger(RunnableAMQConsumer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(RunnableAMQConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void shutdown() {
        running = false;
    }

}
