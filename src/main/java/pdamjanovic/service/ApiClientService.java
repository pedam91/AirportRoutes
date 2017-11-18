package pdamjanovic.service;

import pdamjanovic.model.Airport;
import pdamjanovic.model.Route;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by p.damjanovic on 11/18/2017.
 */
public interface ApiClientService {

    public Airport[] getAirports() throws IOException;

    public Route[] getRoutes() throws IOException;
}
