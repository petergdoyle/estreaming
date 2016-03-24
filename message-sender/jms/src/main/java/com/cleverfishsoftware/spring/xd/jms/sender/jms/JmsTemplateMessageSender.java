/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.jms;

import com.cleverfishsoftware.spring.xd.jms.sender.MessageSender;
import javax.jms.ConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author peter
 */
public class JmsTemplateMessageSender extends AbstractJMSSender implements MessageSender {

    private static JmsTemplateMessageSender instance;

    private final JmsTemplate template;

    public static MessageSender getInstance(ConnectionFactory cf, String brokerUrl, String queueName) {
        instance = new JmsTemplateMessageSender(cf, brokerUrl, queueName);
        return instance;
    }

    private JmsTemplateMessageSender(ConnectionFactory cf, String brokerUrl, String queueName) {
        super(brokerUrl, queueName);
        template = new JmsTemplate(cf);
    }

    @Override
    public void send(String payload) {
        template.convertAndSend(queueName, payload);
    }
}
