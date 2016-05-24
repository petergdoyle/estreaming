/*
 */
package com.cleverfishsoftware.loadgenerator;

import java.util.Properties;

/**
 *
 * @author peter
 */
public interface MessageSenderBuilder {

    MessageSender getInstance(final Properties props) throws Exception;
}
