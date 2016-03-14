/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

/**
 *
 * @author peter
 */
public class ConsoleTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("starting...");
        for (int i = 0; i < 100; i++) {
            System.out.print("#");
            if (i % 20 == 0) {
                System.out.print("\r                    \r");
            }
            Thread.sleep(100);
        }
        System.out.println("\ndone");
    }

}
