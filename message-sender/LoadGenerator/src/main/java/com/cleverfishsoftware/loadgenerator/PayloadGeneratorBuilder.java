/*
 */
package com.cleverfishsoftware.loadgenerator;

import java.util.Properties;

/**
 *
 * @author peter
 */
public interface PayloadGeneratorBuilder {

    public PayloadGenerator getInstance(Properties properties) throws Exception;
}
