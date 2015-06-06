/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

/**
 *
 * @author peter.doyle
 */
public class Flight {

    final public Airport airport;
    final public Airline from;
    final public Airline to;
    final public String price;
    final public String takeoffTime;
    final public String landingTime;
    final public String type;

    public Flight(Airport airport, Airline from, Airline to, String price, String takeoffTime, String landingTime, String type) {
        this.airport = airport;
        this.from = from;
        this.to = to;
        this.price = price;
        this.takeoffTime = takeoffTime;
        this.landingTime = landingTime;
        this.type = type;
    }
}
