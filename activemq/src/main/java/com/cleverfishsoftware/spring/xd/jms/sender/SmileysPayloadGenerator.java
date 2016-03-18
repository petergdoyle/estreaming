/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 *
 * @author peter
 */
public class SmileysPayloadGenerator implements PayloadGenerator {

    private static ByteBuffer BUFFER;

    public SmileysPayloadGenerator() {
    }

    @Override
    public String[] getPayload(final int size) {
        int buffersize = size * 2; //the charBuffer will need to be sized appropriately 
        if (BUFFER == null) {
            //System.out.println("initial buffer allocation");
            BUFFER = ByteBuffer.allocateDirect(buffersize);
            fillBuffer(BUFFER);
        } else if (buffersize <= BUFFER.capacity()) {
            //System.out.println("buffer is large enough");
        } else {
            //System.out.println("buffer size needs to be increased");
            synchronized (this) {
                BUFFER = resizeBuffer(BUFFER, buffersize);
                fillBuffer(BUFFER);
            }
        }
        char[] data = extractChars(BUFFER, size);
        return new String[]{new String(data)};

    }

    private void fillBuffer(final ByteBuffer buffer) {
        char white_smiley = '\u263A';
        char black_smiley = '\u263B';
        int charCount = 0;
        //System.out.println("filling buffer from position " + buffer.position());
        while (buffer.hasRemaining()) {
            if (charCount % 2 == 0) {
                buffer.putChar(black_smiley);
            } else {
                buffer.putChar(white_smiley);
            }
            charCount++;
        }
        //System.out.println("buffer position after fill " + buffer.position());
    }

    public static ByteBuffer resizeBuffer(final ByteBuffer original, final int size) {
        ByteBuffer resizedBuffer = ByteBuffer.allocate(size);
        original.rewind();//copy from the beginning
        resizedBuffer.put(original);
        //resizedBuffer.flip(); // don't flip it, as it will be filled from the current position rather than starting from zero
        return resizedBuffer;
    }

    private char[] extractChars(final ByteBuffer buffer, final int size) {
        //System.out.println("bytebuffer capacity: " + buffer.capacity());
        ByteBuffer duplicate = buffer.duplicate();
        duplicate.rewind();
        CharBuffer charBuffer = duplicate.asCharBuffer();
        charBuffer.rewind();
        //System.out.println("charBuffer capacity: " + charBuffer.capacity());
        char[] array = new char[charBuffer.capacity()];
        charBuffer.get(array);
        return array;
    }

    public static void main(String[] args) {
        int size = 0;
        if (args.length > 0) {
            String argValue = args[0];
            if (argValue != null && argValue.length() > 0) {
                size = Integer.parseInt(argValue);
            }
        }
        SmileysPayloadGenerator payloadGenerator = new SmileysPayloadGenerator();
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
