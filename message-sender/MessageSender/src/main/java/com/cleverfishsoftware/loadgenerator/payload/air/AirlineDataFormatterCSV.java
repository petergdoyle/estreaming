/*]
 */
package com.cleverfishsoftware.loadgenerator.payload.air;

import static com.cleverfishsoftware.loadgenerator.payload.air.AirlineDataPayloadGenerator.removeBadChars;
import java.util.UUID;

/**
 *
 * @author peter
 */
public class AirlineDataFormatterCSV implements AirlineDataFormatter {

    private static final String COMMA = ",";

    @Override
    public String format(Airline airline, Airport from, Airport to, int depHr, int depMin, int arrHr, int arrMin, String price, String currency, String type, int size) {
        StringBuilder record = new StringBuilder()
                .append(UUID.randomUUID().toString())
                .append(COMMA)
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
        return record.toString();
    }
}
