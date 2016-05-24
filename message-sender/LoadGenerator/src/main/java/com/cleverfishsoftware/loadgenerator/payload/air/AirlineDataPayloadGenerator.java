/*
 */
package com.cleverfishsoftware.loadgenerator.payload.air;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import static com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataSource.AIRLINES;
import static com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataSource.AIRPORTS;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author peter
 */
public class AirlineDataPayloadGenerator implements PayloadGenerator {

    private static final int MIN_PRICE = 250;
    private static final int MAX_PRICE = 1600;

    private static final Random RANDOM = new Random();
    private static final Formatter PRICE_FORMATTER = new Formatter();

    private final List<Airline> airlines = new ArrayList<>();
    private final List<Airport> airports = new ArrayList<>();

    private final AirlineDataFormatter airlineDataFormatter;

    public AirlineDataPayloadGenerator() {
        this.airlineDataFormatter = new AirlineDataFormatterCSV();
    }

    public AirlineDataPayloadGenerator(AirlineDataFormatter airlineDataFormatter) {
        this.airlineDataFormatter = airlineDataFormatter;
    }

    @Override
    public String[] getPayload(int size) {
        Airline airline = selectRandomAirline();
        Set<Airport> excludes = new HashSet<>();
        Airport from = selectRandomFromAirport(airline, excludes);
        excludes.add(from);
        Airport to = selectRandomToAirport(excludes);
        String price = PRICE_FORMATTER.format(Locale.US, "%.2f", selectRandomPrice()).toString();
        String type = airline.country.equals(to.country) ? "DOM" : "INTL";
        String currency = "USD";

        int maxFlights = 4;
        int minFlights = 1;
        int numFlights = RANDOM.nextInt((maxFlights + 1) - minFlights) + minFlights;

        String[] payloads = new String[numFlights];

        for (int i = 0; i < numFlights; i++) {
            int depHr = RANDOM.nextInt((23 + 1) - 0) + 0;
            int depMin = RANDOM.nextInt((59 + 1) - 0) + 0;
            int arrHr = RANDOM.nextInt(((23) + 1) - depHr) + depHr;
            int arrMin = RANDOM.nextInt(((59) + 1) - depMin) + depMin;
            String record = airlineDataFormatter.format(airline, from, to, depHr, depMin, arrHr, arrMin, price, currency, type, size);
            payloads[i] = record;
        }
        return payloads;

    }

    static String removeBadChars(String s) {
        return s.replace(",", "");
    }

    private static Airline selectRandomAirline() {
        Airline selected = AIRLINES.get(RANDOM.nextInt(AIRLINES.size()));
        return selected;
    }

    private static Airport selectRandomFromAirport(Airline airline, Set<Airport> excludes) {
        Airport airport = AIRPORTS.get(RANDOM.nextInt(AIRPORTS.size()));
        if (excludes.contains(airport) | !airline.country.equals(airport.country)) {
            boolean add = excludes.add(airport);
            return selectRandomFromAirport(airline, excludes);
        } else {
            return airport;
        }
    }

    private static Airport selectRandomToAirport(Set<Airport> excludes) {
        Airport airport = AIRPORTS.get(RANDOM.nextInt(AIRPORTS.size()));
        if (excludes.contains(airport)) {
            boolean add = excludes.add(airport);
            return selectRandomToAirport(excludes);
        } else {
            return airport;
        }
    }

    private static float selectRandomPrice() {
        return RANDOM.nextInt((MAX_PRICE + 1) - MIN_PRICE) + MIN_PRICE;
    }

}
