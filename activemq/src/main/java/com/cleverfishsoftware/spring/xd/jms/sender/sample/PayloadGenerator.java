/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.sample;

/**
 *
 * @author peter
 */
public interface PayloadGenerator {
    
    /**
     * return some content to be considered message payload. 
     * content can be generated in multiple chunks or records and therefore a batch of payload strings can be
     * returned all at once
     * @param size the size of the content record 
     * @return at least one String, possibly more if the type of content makes sense to be generated as a batch
     */
    String[] getPayload(int size);  
    
}
