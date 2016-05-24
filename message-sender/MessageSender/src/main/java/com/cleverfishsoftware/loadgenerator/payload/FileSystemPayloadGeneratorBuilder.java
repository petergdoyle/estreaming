/*
 */
package com.cleverfishsoftware.loadgenerator.payload;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import com.cleverfishsoftware.loadgenerator.PayloadGeneratorBuilder;
import java.util.Properties;
import static com.cleverfishsoftware.loadgenerator.Common.NullOrEmpty;

/**
 *
 * @author peter
 */
public class FileSystemPayloadGeneratorBuilder implements PayloadGeneratorBuilder {

    @Override
    public PayloadGenerator getInstance(Properties properties) throws Exception {
        String fn = properties.getProperty(LOAD_GENERATOR_FILE_SYSTEM_PAYLOAD_GENERATORFI);
        if (NullOrEmpty(fn)) {
            throw new RuntimeException("missing system property: " + LOAD_GENERATOR_FILE_SYSTEM_PAYLOAD_GENERATORFI);
        }
        return new FileSystemPayloadGenerator2(fn);

    }
    private static final String LOAD_GENERATOR_FILE_SYSTEM_PAYLOAD_GENERATORFI = "LoadGenerator.FileSystemPayloadGenerator.file";

}
