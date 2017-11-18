package pdamjanovic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by p.damjanovic on 11/18/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {

    @JsonProperty("latitude")
    private double lat;

    @JsonProperty("longitude")
    private double lng;

    private int airportID;
    private String name;
    private String city;
    private String country;
    // 3-letter IATA code. Null if not assigned/unknown.
    private String IATA;

    public Airport() {
    }


    public Airport(double lat, double lng) {

        this.lat = lat;
        this.lng = lng;
    }

    public Airport(double lat, double lng, int airportID, String name, String city, String country, String IATA) {
        this.lat = lat;
        this.lng = lng;
        this.airportID = airportID;
        this.name = name;
        this.city = city;
        this.country = country;
        this.IATA = IATA;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getAirportID() {
        return airportID;
    }

    public void setAirportID(int airportID) {
        this.airportID = airportID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }
}
