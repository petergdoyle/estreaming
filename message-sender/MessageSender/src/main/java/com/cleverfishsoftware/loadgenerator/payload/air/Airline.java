/*
 */
package com.cleverfishsoftware.loadgenerator.payload.air;

import java.util.Objects;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author peter.doyle
 */
public class Airline implements Selectable {

    final public String id;
    final public String name;
    final public String alias;
    final public String iataCode;
    final public String icaoCode;
    final public String callSign;
    final public String country;
    final public String active;
    final public String code;

    public Airline(CSVRecord record) {
        int i = 0;
        id = record.get(i++);
        name = record.get(i++);
        alias = record.get(i++);
        iataCode = record.get(i++);
        icaoCode = record.get(i++);
        callSign = record.get(i++);
        country = record.get(i++);
        active = record.get(i++);
        code = !iataCode.equals("") ? iataCode : icaoCode;
    }

    
    @Override
    public boolean isSelectable() {
        if (!(country.equals("United Kingdom") || (country.equals("Canada") || (country.equals("United States"))))) {
            return false;
        }
        if (!active.equals("Y")) {
            return false;
        }
        if (!code.matches("[A-Z]{3}")) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "Airline{" + "id=" + id + ", name=" + name + ", alias=" + alias + ", iataCode=" + iataCode + ", icaoCode=" + icaoCode + ", callSign=" + callSign + ", country=" + country + ", active=" + active + '}';
        return id + "," + name + "," + alias + "," + iataCode + "," + icaoCode + "," + callSign + "," + country + "," + active;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Airline other = (Airline) obj;
        return Objects.equals(this.id, other.id);
    }

}
