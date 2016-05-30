/*
 */
package com.cleverfishsoftware.estreaming.streamprocessor;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author peter
 */
public class KafkaStreamHandler extends SimpleChannelInboundHandler<String> {
    
    private static AtomicInteger instance = new AtomicInteger(0);
    private final ExecutorService executor;
    
    public KafkaStreamHandler() {
        
        String bootstrapServers = System.getProperty("bootstrap.servers");
        if (bootstrapServers == null || bootstrapServers.length() == 0) {
            bootstrapServers = "localhost:9092";
        }
        String consumerGroupId = System.getProperty("group.id");
        if (consumerGroupId == null || consumerGroupId.length() == 0) {
            consumerGroupId = "KafkaStreamHandler";
        }
        String consumerId = System.getProperty("consumer.id");
        if (consumerId == null || consumerId.length() == 0) {
            consumerId = "KafkaStreamHandler-" + instance.incrementAndGet();
        }
        String topicOffset = System.getProperty("topicOffset");
        if (topicOffset == null || topicOffset.length() == 0) {
            topicOffset = "--from-beginning";
        }
        String topic = System.getProperty("topic");
        if (topic == null || topic.length() == 0) {
            topic = "topic-1";
        }
        
        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", bootstrapServers);
        kafkaProperties.put("group.id", consumerGroupId);
        kafkaProperties.put("enable.auto.commit", "true");
        kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put("session.timeout.ms", "10000");
        kafkaProperties.put("fetch.min.bytes", "50000");
        kafkaProperties.put("receive.buffer.bytes", "262144");
        kafkaProperties.put("max.partition.fetch.bytes", "2097152");
        
        executor = Executors.newSingleThreadExecutor();
        
        RunnableKafkaConsumer consumer = new RunnableKafkaConsumer(consumerId, kafkaProperties, Arrays.asList(new String[]{topic}), 0);
        executor.submit(consumer);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("HELO: Type the path of the file to retrieve.\n");
    }
    
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        
        if (ctx.channel().isActive()) {
            ctx.writeAndFlush("ERR: "
                    + cause.getClass().getSimpleName() + ": "
                    + cause.getMessage() + '\n').addListener(ChannelFutureListener.CLOSE);
        }
    }
}
