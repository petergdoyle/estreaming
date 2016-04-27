/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.generator;

import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGenerator;
import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGeneratorBuilder;
import java.util.Properties;

/**
 *
 * @author peter
 */
public class OnesAndZerosPayloadGeneratorBuilder implements PayloadGeneratorBuilder {

    @Override
    public PayloadGenerator getInstance(Properties properties) throws Exception {
        return new OnesAndZerosPayloadGenerator();
    }
    
}
