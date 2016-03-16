/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.airline;

import java.util.Objects;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author peter.doyle
 */
public class Airport {

    final public String id;
    final public String name;
    final public String city;
    final public String country;
    final public String code;
    final public String latitude;
    final public String longitude;
    final public String altitude;
    final public String timeZone;
    final public String dst;
    final public String tz;

    public Airport(CSVRecord record) {
        int i = 0;
        id = record.get(i++);
        name = record.get(i++);
        city = record.get(i++);
        country = record.get(i++);
        code = record.get(i++);
        latitude = record.get(i++);
        longitude = record.get(i++);
        altitude = record.get(i++);
        timeZone = record.get(i++);
        dst = record.get(i++);
        tz = record.get(i++);
    }

    public boolean isSelectable() {
        if (!(country.equals("United Kingdom") || (country.equals("Canada") || (country.equals("United States"))))) {
            return false;
        }
        if (!code.matches("[A-Z]{3}")) {
            return false;
        }
        return true;
    }

    public String getHeaderLine() {
        return "airport_id,airport_name,airport_city,airport_country,airport_faaCode,airport_iataCode,airport_icaoCode,airport_latitude,airport_longitude,airport_altitude,airport_timeZone,airport_ds,airport_tz";
    }

    @Override
    public String toString() {
        return id + ", " + "," + city + "," + country + "," + code + "," + code + "," + latitude + "," + longitude + "," + altitude + "," + timeZone + "," + dst + "," + tz;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Airport other = (Airport) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
