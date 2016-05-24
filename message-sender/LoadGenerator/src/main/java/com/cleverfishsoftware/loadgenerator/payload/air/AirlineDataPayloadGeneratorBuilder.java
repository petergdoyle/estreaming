/*
 */
package com.cleverfishsoftware.loadgenerator.payload.air;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import com.cleverfishsoftware.loadgenerator.PayloadGeneratorBuilder;
import java.util.Properties;

/**
 *
 * @author peter
 */
public class AirlineDataPayloadGeneratorBuilder implements PayloadGeneratorBuilder {

    @Override
    public PayloadGenerator getInstance(Properties properties) throws Exception {
        return new AirlineDataPayloadGenerator(new AirlineDataFormatterJSON());
    }

}
