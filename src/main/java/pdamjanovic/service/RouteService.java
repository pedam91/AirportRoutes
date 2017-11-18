package pdamjanovic.service;

import pdamjanovic.calculation.CostPathPair;
import pdamjanovic.model.Airport;

import java.io.IOException;
import java.util.Map;

/**
 * Created by p.damjanovic on 11/18/2017.
 */
public interface RouteService {

    public CostPathPair<Integer> findRoute(Map<String, Airport> airports, String start, String end) throws IOException;
}
