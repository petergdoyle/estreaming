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
public class SmileysPayloadGeneratorBuilder implements PayloadGeneratorBuilder {
    
    @Override
    public PayloadGenerator getInstance(Properties properties) throws Exception {
        return new SmileysPayloadGenerator();
    }
    
}
