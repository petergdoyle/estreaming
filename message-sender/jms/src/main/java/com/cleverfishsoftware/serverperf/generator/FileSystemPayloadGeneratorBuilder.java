/*
 */
package com.cleverfishsoftware.serverperf.generator;

import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGenerator;
import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGeneratorBuilder;
import java.util.Properties;

/**
 *
 * @author peter
 */
public class FileSystemPayloadGeneratorBuilder implements PayloadGeneratorBuilder {

    private PayloadGenerator instance;

    @Override
    public PayloadGenerator getInstance(Properties properties) throws Exception {
        if (instance == null) {
            String propValue = properties.getProperty("FileSystemPayloadGenerator.file");
            if (propValue == null || propValue.length() == 0) {
                throw new java.lang.IllegalArgumentException("require build property \"FileSystemPayloadGenerator.file\" is missing");
            }
            String fn = properties.getProperty("FileSystemPayloadGenerator.file");
            instance = new com.cleverfishsoftware.serverperf.generator.FileSystemPayloadGenerator(fn);
        }
        return instance;
    }

    private class FileSystemPayloadGenerator implements PayloadGenerator {

        @Override
        public String[] getPayload(int size) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
