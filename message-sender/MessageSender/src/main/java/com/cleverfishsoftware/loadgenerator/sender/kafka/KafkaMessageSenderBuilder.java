/*
 */
package com.cleverfishsoftware.loadgenerator.sender.kafka;

import com.cleverfishsoftware.loadgenerator.MessageSender;
import com.cleverfishsoftware.loadgenerator.MessageSenderBuilder;
import java.util.Properties;
import static com.cleverfishsoftware.loadgenerator.Common.NullOrEmpty;

/**
 *
 * @author peter
 */
public class KafkaMessageSenderBuilder implements MessageSenderBuilder {

    @Override
    public MessageSender getInstance(final Properties props) throws Exception {

        String brokerUrl = props.getProperty(LOAD_GENERATOR_KAFKA_MESSAGE_SENDER_BUILDERBRO);
        if (NullOrEmpty(brokerUrl)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_KAFKA_MESSAGE_SENDER_BUILDERBRO);
        }
        String topic = props.getProperty(LOAD_GENERATOR_KAFKA_MESSAGE_SENDER_BUILDERTOP);
        if (NullOrEmpty(topic)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_KAFKA_MESSAGE_SENDER_BUILDERBRO);
        }

        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", brokerUrl);
        kafkaProperties.put("acks", "all");
        kafkaProperties.put("retries", "0");
        kafkaProperties.put("batch.size", "16384");
        kafkaProperties.put("auto.commit.interval.ms", "1000");
        kafkaProperties.put("linger.ms", "0");
        kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("max.block.ms", "60000");
        
        return new KafkaMessageSender(kafkaProperties, topic);

    }
    private static final String LOAD_GENERATOR_KAFKA_MESSAGE_SENDER_BUILDERTOP = "LoadGenerator.KafkaMessageSenderBuilder.topic_name";
    private static final String LOAD_GENERATOR_KAFKA_MESSAGE_SENDER_BUILDERBRO = "LoadGenerator.KafkaMessageSenderBuilder.broker_url";

}
