package pdamjanovic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by p.damjanovic on 11/18/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

    private String fromAirport;
    private String toAirport;

    public Route() {

    }

    public Route(String fromAirport, String toAirport) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }
}
