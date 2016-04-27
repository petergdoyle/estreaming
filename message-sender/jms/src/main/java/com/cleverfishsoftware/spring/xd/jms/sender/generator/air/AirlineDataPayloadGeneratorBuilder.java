/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.generator.air;

import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGenerator;
import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGeneratorBuilder;
import java.util.Properties;

/**
 *
 * @author peter
 */
public class AirlineDataPayloadGeneratorBuilder implements PayloadGeneratorBuilder {

    @Override
    public PayloadGenerator getInstance(Properties properties) throws Exception {
        return new AirlineDataPayloadGenerator();
    }

}
