/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.sample;

/**
 *
 * @author peter
 */
public interface MessageSender {
    
    void send(String payload) throws Exception;
    
}
