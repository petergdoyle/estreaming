/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms;

import com.cleverfishsoftware.loadgenerator.MessageSender;
import javax.jms.ConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author peter
 */
public class SpringJmsTemplateMessageSender extends AbstractJMSSender implements MessageSender {

    private static SpringJmsTemplateMessageSender instance;

    private final JmsTemplate template;

    public static MessageSender getInstance(ConnectionFactory cf, String brokerUrl, String queueName) {
        instance = new SpringJmsTemplateMessageSender(cf, brokerUrl, queueName);
        return instance;
    }

    private SpringJmsTemplateMessageSender(ConnectionFactory cf, String brokerUrl, String queueName) {
        super(brokerUrl, queueName);
        template = new JmsTemplate(cf);
    }

    @Override
    public void send(String payload) {
        template.convertAndSend(queueName, payload);
    }
}
