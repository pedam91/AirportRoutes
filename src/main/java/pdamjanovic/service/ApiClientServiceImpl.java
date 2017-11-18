package pdamjanovic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pdamjanovic.model.Airport;
import pdamjanovic.model.Route;

import java.io.IOException;
import java.net.URL;

/**
 * Created by p.damjanovic on 11/18/2017.
 */
@Service
public class ApiClientServiceImpl implements ApiClientService {

    private final static String AIRPORTS_URL = "https://gitlab.levi9.com/n.cvijovic/airports/raw/master/airports.json";
    private final static String ROUTES_URL = "https://gitlab.levi9.com/n.cvijovic/routes/raw/master/routes.json";

    @Override
    public Airport[] getAirports() throws IOException {
        return  new ObjectMapper().readValue(new URL(AIRPORTS_URL), Airport[].class);
    }

    @Override
    public Route[] getRoutes() throws IOException {
        return  new ObjectMapper().readValue(new URL(ROUTES_URL), Route[].class);
    }
}
