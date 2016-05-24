/*
 */
package com.cleverfishsoftware.loadgenerator.payload.air;

/**
 *
 * @author peter
 */
public interface AirlineDataFormatter {

    String format(Airline airline, Airport from, Airport to, int depHr, int depMin, int arrHr, int arrMin, String price, String currency, String type, int size);
    
}
