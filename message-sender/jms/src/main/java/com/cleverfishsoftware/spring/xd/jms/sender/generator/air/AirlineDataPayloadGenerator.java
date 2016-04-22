/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.generator.air;

import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGenerator;
import com.cleverfishsoftware.spring.xd.jms.sender.generator.SmileysPayloadGenerator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author peter
 */
public class AirlineDataPayloadGenerator implements PayloadGenerator {

    private static final String COMMA = ",";
    private static final int MIN_PRICE = 350;
    private static final int MAX_PRICE = 1600;

    private static final List<Airline> AIRLINES = new ArrayList<>();
    private static final List<Airport> AIRPORTS = new ArrayList<>();

    static {
        try {

            InputStream in;
            Iterable<CSVRecord> records;
            in = AirlineDataPayloadGenerator.class.getClassLoader().getResourceAsStream("airlines.dat");
            records = CSVFormat.DEFAULT.parse(new BufferedReader(new InputStreamReader(in)));
            for (CSVRecord each : records) {
                Airline airline = new Airline(each);
                if (airline.isSelectable()) {
                    AIRLINES.add(airline);
                }
            }
            in = AirlineDataPayloadGenerator.class.getClassLoader().getResourceAsStream("airports.dat");
            records = CSVFormat.DEFAULT.parse(new BufferedReader(new InputStreamReader(in)));
            for (CSVRecord each : records) {
                Airport airport = new Airport(each);
                if (airport.isSelectable()) {
                    AIRPORTS.add(airport);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(AirlineDataPayloadGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static final Random random = new Random();
    private static final Formatter priceFormatter = new Formatter();

    @Override
    public String[] getPayload(int size) {
        Airline airline = selectRandomAirline();
        Set<Airport> excludes = new HashSet<>();
        Airport from = selectRandomFromAirport(airline, excludes);
        excludes.add(from);
        Airport to = selectRandomToAirport(excludes);
        String price = new Formatter().format(Locale.US, "%.2f", selectRandomPrice()).toString();
        String type = airline.country.equals(to.country) ? "DOM" : "INTL";
        String currency = "USD";

        int maxFlights = 4;
        int minFlights = 1;
        int numFlights = random.nextInt((maxFlights + 1) - minFlights) + minFlights;

        String[] payloads = new String[numFlights];

        for (int i = 0; i < numFlights; i++) {
            int depHr = random.nextInt((23 + 1) - 0) + 0;
            int depMin = random.nextInt((59 + 1) - 0) + 0;
            int arrHr = random.nextInt(((23) + 1) - depHr) + depHr;
            int arrMin = random.nextInt(((59) + 1) - depMin) + depMin;

            StringBuilder record = new StringBuilder()
                    .append(removeBadChars(airline.code))
                    .append(COMMA)
                    .append(removeBadChars(airline.name))
                    .append(COMMA)
                    .append(removeBadChars(airline.country))
                    .append(COMMA)
                    .append(removeBadChars(from.code))
                    .append(COMMA)
                    .append(removeBadChars(from.name))
                    .append(COMMA)
                    .append(removeBadChars(from.city))
                    .append(COMMA)
                    .append(removeBadChars(from.country))
                    .append(COMMA)
                    .append(removeBadChars(to.code))
                    .append(COMMA)
                    .append(removeBadChars(to.name))
                    .append(COMMA)
                    .append(removeBadChars(to.city))
                    .append(COMMA)
                    .append(removeBadChars(to.country))
                    .append(COMMA)
                    .append(String.format("%02d", depHr).concat(":").concat(String.format("%02d", depMin)))
                    .append(COMMA)
                    .append(String.format("%02d", arrHr).concat(":").concat(String.format("%02d", arrMin)))
                    .append(COMMA)
                    .append(price)
                    .append(COMMA)
                    .append(currency)
                    .append(COMMA)
                    .append(type);
            if (record.length() + 2 < size) {
                record.append(COMMA);
                while (record.length() < size) { // add padding to match the desired message size, need at least one character after the comma
                    if (record.length() % 2 == 0) {
                        record.append("0");
                    } else {
                        record.append("1");
                    }
                }
            }
            payloads[i] = record.toString();
        }
        return payloads;

    }

    private static String removeBadChars(String s) {
        return s.replace(",", "");
    }

    private static Airline selectRandomAirline() {
        Airline selected = AIRLINES.get(random.nextInt(AIRLINES.size()));
        return selected;
    }

    private static Airport selectRandomFromAirport(Airline airline, Set<Airport> excludes) {
        Airport airport = AIRPORTS.get(random.nextInt(AIRPORTS.size()));
        if (excludes.contains(airport) | !airline.country.equals(airport.country)) {
            boolean add = excludes.add(airport);
            return selectRandomFromAirport(airline, excludes);
        } else {
            return airport;
        }
    }

    private static Airport selectRandomToAirport(Set<Airport> excludes) {
        Airport airport = AIRPORTS.get(random.nextInt(AIRPORTS.size()));
        if (excludes.contains(airport)) {
            boolean add = excludes.add(airport);
            return selectRandomToAirport(excludes);
        } else {
            return airport;
        }
    }

    private static float selectRandomPrice() {
        return random.nextInt((MAX_PRICE + 1) - MIN_PRICE) + MIN_PRICE;
    }

}
