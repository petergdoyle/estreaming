/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.generator;

import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGenerator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author peter
 */
public class SequentialNumberPayloadGenerator implements PayloadGenerator {

    private final AtomicInteger count = new AtomicInteger(0);
    private StringBuilder payload;

    @Override
    public String[] getPayload(final int size) {
        int templateLength = 10;
        String template = "%0" + templateLength + "d";
        if (payload == null) {
            int charCount = 0;
            int capacity = size - templateLength;
            payload = new StringBuilder(capacity);
            payload.append(template);
            while (payload.length() < capacity) {
                if (charCount % 2 == 0) {
                    payload.append("<");
                } else {
                    payload.append(">");
                }
                charCount++;
            }
        }

        return new String[]{String.format(payload.toString(), count.incrementAndGet())};
    }

}
