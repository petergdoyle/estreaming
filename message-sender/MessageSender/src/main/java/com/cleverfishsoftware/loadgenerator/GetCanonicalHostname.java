/*

 */
package com.cleverfishsoftware.loadgenerator;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author peter
 */
public class GetCanonicalHostname {

    public static void main(String[] args) throws UnknownHostException {

        byte[] ipAddr = new byte[]{127, 0, 0, 1};
        InetAddress addr = InetAddress.getByName("localhost");
        System.out.println(addr.getCanonicalHostName());
    }

}
