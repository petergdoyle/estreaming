/*
 */
package com.cleverfishsoftware.loadgenerator.payload.air;

import static com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataSource.AIRLINES;
import static com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataSource.AIRPORTS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author peter
 */
public class AirlineDataFormatterJSON implements AirlineDataFormatter {

    ObjectMapper mapper = new ObjectMapper();

    private final static Map<String, Object> FIELD_MAP = new LinkedHashMap<>();

    static {
        initializeFieldMap();
    }

    @Override
    public String format(Airline airline, Airport from, Airport to, int depHr, int depMin, int arrHr, int arrMin, String price, String currency, String type, int size) {

        try {
            FIELD_MAP.put("airlineCd", airline.name);
            FIELD_MAP.put("airlineNm", airline.country);
            FIELD_MAP.put("airlineCntry", airline.country);
            FIELD_MAP.put("depAirportCd", from.id);
            FIELD_MAP.put("depAirportNm", from.name);
            FIELD_MAP.put("depAirportCty", from.city);
            FIELD_MAP.put("depAirportCntry", from.country);
            FIELD_MAP.put("arrAirportCd", to.id);
            FIELD_MAP.put("arrAirportNm", to.name);
            FIELD_MAP.put("arrAirportCty", to.city);
            FIELD_MAP.put("arrAirportCntry", to.country);
            FIELD_MAP.put("depTime", String.format("%02d", depHr).concat(":").concat(String.format("%02d", depMin)));
            FIELD_MAP.put("arrTime", String.format("%02d", arrHr).concat(":").concat(String.format("%02d", arrMin)));
            FIELD_MAP.put("price", price);
            FIELD_MAP.put("currency", currency);
            FIELD_MAP.put("type", type);
            FIELD_MAP.put("padding", "");

            String json = mapper.writeValueAsString(FIELD_MAP);
            if (json.length() < size) {
                StringBuilder sb = new StringBuilder(json.length());
                char[] padding = new char[]{'p', 'a', 'd', 'd', 'i', 'n', 'g'};
                int i = 0;
                while (sb.capacity() < size) {
                    sb.append(padding[i]);
                    ++i;
                    if (i == padding.length) {
                        i = 0;
                    }
                }
                FIELD_MAP.put("padding", sb.toString());
                json = mapper.writeValueAsString(FIELD_MAP);
            }
            return json;

        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

    }

    static private void initializeFieldMap() {

        FIELD_MAP.put("airlineCd", "");
        FIELD_MAP.put("airlineNm", "");
        FIELD_MAP.put("airlineCntry", "");
        FIELD_MAP.put("depAirportCd", "");
        FIELD_MAP.put("depAirportNm", "");
        FIELD_MAP.put("depAirportCty", "");
        FIELD_MAP.put("depAirportCntry", "");
        FIELD_MAP.put("arrAirportCd", "");
        FIELD_MAP.put("arrAirportNm", "");
        FIELD_MAP.put("arrAirportCty", "");
        FIELD_MAP.put("arrAirportCntry", "");
        FIELD_MAP.put("depTime", "");
        FIELD_MAP.put("arrTime", "");
        FIELD_MAP.put("price", "");
        FIELD_MAP.put("currency", "");
        FIELD_MAP.put("type", "");
        FIELD_MAP.put("padding", "");

    }

    public static void main(String[] args) {

        AirlineDataFormatterJSON dataFormatter = new AirlineDataFormatterJSON();
        Airline airline = AIRLINES.get(0);
        Airport from = AIRPORTS.get(0);
        Airport to = AIRPORTS.get(1);
        
        int depHr = 11;
        int depMin = 00;
        int arrHr = 13;
        int arrMin = 15;
        String price = "425.00";
        String currency = "USD";
        String type = "DOM";
        int size = 1024;

        String json = dataFormatter.format(airline, from, to, depHr, depMin, arrHr, arrMin, price, currency, type, size);
        System.out.println(json);
        
    }

}
