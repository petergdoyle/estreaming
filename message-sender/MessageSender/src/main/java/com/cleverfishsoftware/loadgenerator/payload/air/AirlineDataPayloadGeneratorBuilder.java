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

        String airlineDataFormatterType = properties.getProperty("LoadGenerator.AirlineDataPayloadGeneratorBuilder.airlineDataFormatterType");
        Class<AirlineDataFormatter> dataFormatterClass = (Class<AirlineDataFormatter>) Class.forName(airlineDataFormatterType);
        AirlineDataFormatter airlineDataFormatter = dataFormatterClass.newInstance();

        return new AirlineDataPayloadGenerator(airlineDataFormatter);
    }

}
