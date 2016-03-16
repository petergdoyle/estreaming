/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

/**
 *
 * @author peter
 */
public interface MessageSender {
    
    void send(String payload) throws Exception;
    
}
