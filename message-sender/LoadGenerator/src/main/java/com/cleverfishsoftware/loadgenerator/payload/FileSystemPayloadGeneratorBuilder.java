/*
 */
package com.cleverfishsoftware.loadgenerator.payload;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import com.cleverfishsoftware.loadgenerator.PayloadGeneratorBuilder;
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
            instance = new com.cleverfishsoftware.loadgenerator.payload.FileSystemPayloadGenerator(fn);
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
