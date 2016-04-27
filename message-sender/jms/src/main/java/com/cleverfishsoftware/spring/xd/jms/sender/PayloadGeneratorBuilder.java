/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import java.util.Properties;

/**
 *
 * @author peter
 */
public interface PayloadGeneratorBuilder {

    public PayloadGenerator getInstance(Properties properties) throws Exception;
}
