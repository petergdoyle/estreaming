/*
 */
package com.cleverfishsoftware.loadgenerator.payload;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author peter
 */
public class SequentialNumberPayloadGenerator implements PayloadGenerator {

    private final AtomicInteger count = new AtomicInteger(0);
    private StringBuilder buffer;

    @Override
    public String[] getPayload(final int size) {
        int templateLength = 10;
        String template = "%0" + templateLength + "d";
        if (buffer == null) {
            int charCount = 0;
            buffer = new StringBuilder(size);
            buffer.append(template);
            int capacity = size - templateLength;
            while (charCount < capacity) {
//                System.out.println("size: " + size + " bufferCapacity: " + buffer.length());
                if (charCount % 4 == 0) {
                    buffer.append(" ");
                } else if (charCount % 2 == 0) {
                    buffer.append("<");
                } else {
                    buffer.append(">");
                }
                charCount++;
            }
        }

        return new String[]{String.format(buffer.toString(), count.incrementAndGet())};
    }

    public static void main(String[] args) throws Exception {

        PayloadGenerator payloadGenerator;
        String[] payloads;
        String payload;
        for (int i = 0; i < 100; i++) {
            payloadGenerator = new SequentialNumberPayloadGeneratorBuilder().getInstance(new Properties());
            int s = 10 + i;
            payloads = payloadGenerator.getPayload(s);
            payload = payloads[0];
            System.out.println(payload + "|i: " + i + "+size:" + payload.length());
        }
        payloadGenerator = new SequentialNumberPayloadGeneratorBuilder().getInstance(new Properties());
        payloads = payloadGenerator.getPayload(100);
        payload = payloads[0];
        System.out.println(payload + "|+size:" + payload.length());
        payloadGenerator = new SequentialNumberPayloadGeneratorBuilder().getInstance(new Properties());
        payloads = payloadGenerator.getPayload(10);
        payload = payloads[0];
        System.out.println(payload + "|+size:" + payload.length());
        payloadGenerator = new SequentialNumberPayloadGeneratorBuilder().getInstance(new Properties());
        payloads = payloadGenerator.getPayload(12);
        payload = payloads[0];
        System.out.println(payload + "|+size:" + payload.length());
        payloadGenerator = new SequentialNumberPayloadGeneratorBuilder().getInstance(new Properties());
        payloads = payloadGenerator.getPayload(20);
        payload = payloads[0];
        System.out.println(payload + "|+size:" + payload.length());

    }
}
