/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.sample;

/**
 *
 * @author peter
 */
public class RandomCharacterPayloadGenerator implements PayloadGenerator {

    private final static String CHUNK = "1234567890"
            + "123456789012345678901234567890"
            + "123456789012345678901234567890"
            + "123456789012345678901234567890";


    public RandomCharacterPayloadGenerator() {
    }

    @Override
    public String[] getPayload(int size) {

        final StringBuilder payloadBuilder = new StringBuilder();
        while (payloadBuilder.length() < size) {
            payloadBuilder.append(CHUNK);
        }
        final String payload = payloadBuilder.substring(0, size - 1);
        return new String[]{payload};
    }

}
