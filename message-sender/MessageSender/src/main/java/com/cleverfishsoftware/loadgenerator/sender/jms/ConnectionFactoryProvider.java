/*
 */
package com.cleverfishsoftware.loadgenerator.sender.jms;

import java.util.Properties;
import javax.jms.ConnectionFactory;

/**
 *
 * @author peter
 */
public interface ConnectionFactoryProvider {

    ConnectionFactory getInstance(Properties props) throws Exception;

}
