/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import java.nio.ByteBuffer;

/**
 *
 * @author peter
 */
public class OnesAndZerosPayloadGenerator implements PayloadGenerator {

    private static ByteBuffer BUFFER;

    public OnesAndZerosPayloadGenerator() {
    }

    @Override
    public String[] getPayload(final int size) {
        if (BUFFER == null) {
            BUFFER = ByteBuffer.allocateDirect(size);
            fillBuffer(BUFFER);
        } else if (size <= BUFFER.capacity()) {
        } else {
            synchronized (this) {
                BUFFER = resizeBuffer(BUFFER, size);
                fillBuffer(BUFFER);
            }
        }
        byte[] data = extractBytes(BUFFER, size);
        return new String[]{new String(data)};
    }

    private byte[] extractBytes(ByteBuffer buffer, int size) {
        byte[] data = new byte[size];
        ByteBuffer duplicate = buffer.duplicate();
        duplicate.rewind();
        duplicate.get(data, 0, size);
        return data;
    }

    private void fillBuffer(ByteBuffer buffer) {
        boolean on = false;
        byte[] one = "1".getBytes();
        byte[] zero = "0".getBytes();
        while (buffer.hasRemaining()) {
            if (on) {
                buffer.put(one);
            } else {
                buffer.put(zero);
            }
            on = !on;
        }
    }

    public static ByteBuffer resizeBuffer(ByteBuffer original, int size) {
        ByteBuffer resizedBuffer = ByteBuffer.allocate(size);
        original.rewind();//copy from the beginning
        resizedBuffer.put(original);
        //resizedBuffer.flip(); // don't flip it, as it will be filled from the current position rather than starting from zero
        return resizedBuffer;
    }

    public static void main(String[] args) {
        int size = 0;
        if (args.length > 0) {
            String argValue = args[0];
            if (argValue != null && argValue.length() > 0) {
                size = Integer.parseInt(argValue);
            }
        }
        OnesAndZerosPayloadGenerator payloadGenerator = new OnesAndZerosPayloadGenerator();
        String[] payloads = payloadGenerator.getPayload(size);
        for (String payload : payloads) {
            System.out.println("payload is " + payload.length() + " bytes long");
            System.out.println(payload);
        }
        payloads = payloadGenerator.getPayload(size);
        for (String payload : payloads) {
            System.out.println("payload is " + payload.length() + " bytes long");
            System.out.println(payload);
        }
        payloads = payloadGenerator.getPayload(size * 2);
        for (String payload : payloads) {
            System.out.println("payload is " + payload.length() + " bytes long");
            System.out.println(payload);
        }
    }
}
