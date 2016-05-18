/*
 */
package com.cleverfishsoftware.loadgenerator.payload.car;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import com.cleverfishsoftware.loadgenerator.PayloadGeneratorBuilder;
import java.util.Properties;

/**
 *
 * @author peter
 */
public class EdifactCarPayloadGeneratorBuilder implements PayloadGeneratorBuilder {

    @Override
    public PayloadGenerator getInstance(Properties properties) throws Exception {
        return new EdifactCarPayloadGenerator();
    }

}
