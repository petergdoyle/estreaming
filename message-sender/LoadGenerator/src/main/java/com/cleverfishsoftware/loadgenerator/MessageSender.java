/*
 */
package com.cleverfishsoftware.loadgenerator;

/**
 *
 * @author peter
 */
public interface MessageSender {
    
    void send(String payload) throws Exception;
    
}
