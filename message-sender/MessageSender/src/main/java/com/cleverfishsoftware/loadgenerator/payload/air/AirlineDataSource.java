/*
 */
package com.cleverfishsoftware.loadgenerator.payload.air;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author peter
 */
public class AirlineDataSource {

    static public final List<Airline> AIRLINES = new ArrayList<>();
    static public final List<Airport> AIRPORTS = new ArrayList<>();

    static {
        try {

            InputStream in;
            Iterable<CSVRecord> records;
            in = AirlineDataSource.class.getClassLoader().getResourceAsStream("airlines.dat");
            records = CSVFormat.DEFAULT.parse(new BufferedReader(new InputStreamReader(in)));
            for (CSVRecord each : records) {
                Airline airline = new Airline(each);
                if (airline.isSelectable()) {
                    AIRLINES.add(airline);
                }
            }
            in = AirlineDataSource.class.getClassLoader().getResourceAsStream("airports.dat");
            records = CSVFormat.DEFAULT.parse(new BufferedReader(new InputStreamReader(in)));
            for (CSVRecord each : records) {
                Airport airport = new Airport(each);
                if (airport.isSelectable()) {
                    AIRPORTS.add(airport);
                }
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
