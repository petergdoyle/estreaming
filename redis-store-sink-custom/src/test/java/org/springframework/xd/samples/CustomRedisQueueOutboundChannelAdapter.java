/*
 */
package org.springframework.xd.samples;

import com.jayway.jsonpath.internal.EvaluationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

/**
 *
 * @author peter
 */
public class CustomRedisQueueOutboundChannelAdapter extends AbstractMessageHandler {

    private final RedisSerializer<String> stringSerializer = new StringRedisSerializer();

    private final RedisTemplate<String, Object> template;

    private final Expression queueNameExpression;

    private volatile StandardEvaluationContext evaluationContext;

    private volatile boolean extractPayload = true;

    private volatile RedisSerializer<?> serializer = new JdkSerializationRedisSerializer();

    private volatile boolean serializerExplicitlySet;

    private volatile boolean leftPush = true;

    public CustomRedisQueueOutboundChannelAdapter(String queueName, RedisConnectionFactory connectionFactory) {
        this(new LiteralExpression(queueName), connectionFactory);
    }

    public CustomRedisQueueOutboundChannelAdapter(Expression queueNameExpression, RedisConnectionFactory connectionFactory) {
        Assert.notNull(queueNameExpression, "'queueNameExpression' is required");
        Assert.hasText(queueNameExpression.getExpressionString(), "'queueNameExpression.getExpressionString()' is required");
        Assert.notNull(connectionFactory, "'connectionFactory' must not be null");
        this.queueNameExpression = queueNameExpression;
        this.template = new RedisTemplate<>();
        this.template.setConnectionFactory(connectionFactory);
        this.template.setEnableDefaultSerializer(false);
        this.template.setKeySerializer(new StringRedisSerializer());
        this.template.afterPropertiesSet();
    }

    public void setExtractPayload(boolean extractPayload) {
        this.extractPayload = extractPayload;
    }

    public void setSerializer(RedisSerializer<?> serializer) {
        Assert.notNull(serializer, "'serializer' must not be null");
        this.serializer = serializer;
        this.serializerExplicitlySet = true;
    }

    /**
     * Specify if {@code PUSH} operation to Redis List should be {@code LPUSH}
     * or {@code RPUSH}.
     *
     * @param leftPush the {@code LPUSH} flag. Defaults to {@code true}.
     * @since 4.3
     */
    public void setLeftPush(boolean leftPush) {
        this.leftPush = leftPush;
    }

    public void setIntegrationEvaluationContext(EvaluationContext evaluationContext) {
        this.evaluationContext = (StandardEvaluationContext) evaluationContext;
    }

    @Override
    public String getComponentType() {
        return "redis:queue-outbound-channel-adapter";
    }

    @Override
    protected void onInit() throws Exception {
        super.onInit();
        if (this.evaluationContext == null) {
            this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(getBeanFactory());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void handleMessageInternal(Message<?> message) throws Exception {
        Object value = message;

        if (this.extractPayload) {
            value = message.getPayload();
        }

        if (!(value instanceof byte[])) {
            if (value instanceof String && !this.serializerExplicitlySet) {
                value = this.stringSerializer.serialize((String) value);
            } else {
                value = ((RedisSerializer<Object>) this.serializer).serialize(value);
            }
        }

        String queueName = this.queueNameExpression.getValue(this.evaluationContext, message, String.class);
        if (this.leftPush) {
            this.template.boundListOps(queueName).leftPush(value);
        } else {
            this.template.boundListOps(queueName).rightPush(value);
        }
    }

}
