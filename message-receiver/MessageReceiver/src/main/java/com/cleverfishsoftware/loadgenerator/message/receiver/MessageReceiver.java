/*
 */
package com.cleverfishsoftware.loadgenerator.message.receiver;

/**
 *
 * @author peter
 */
public interface MessageReceiver extends Runnable {

    void shutdown();

}
