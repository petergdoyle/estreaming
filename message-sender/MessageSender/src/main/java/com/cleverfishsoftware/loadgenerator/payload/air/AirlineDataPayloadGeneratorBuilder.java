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

        String dataFormatterType = properties.getProperty("LoadGenerator.AirlineDataPayloadGeneratorBuilder.dataFormatterType");
        Class<AirlineDataFormatter> dataFormatterClass = (Class<AirlineDataFormatter>) Class.forName(dataFormatterType);
        AirlineDataFormatter airlineDataFormatter = dataFormatterClass.newInstance();

        return new AirlineDataPayloadGenerator(airlineDataFormatter);
    }

}
