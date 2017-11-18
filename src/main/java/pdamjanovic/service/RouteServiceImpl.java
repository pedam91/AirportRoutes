package pdamjanovic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdamjanovic.calculation.*;
import pdamjanovic.model.Airport;
import pdamjanovic.model.Route;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by p.damjanovic on 11/18/2017.
 */
@Service
public class RouteServiceImpl implements RouteService {

    static Map<String, Vertex> vertices = new HashMap<String, Vertex>();
    Set<Edge> edges = new HashSet<Edge>();
    Graph graph;
    Route[] routes;
    Map<String, Airport> airports;

    @Autowired
    ApiClientService apiClientService;

    @Override
    public CostPathPair<Integer> findRoute(Map<String, Airport> airports, String start, String end) throws IOException {
        this.airports = airports;
        routes = apiClientService.getRoutes();

        for(Airport airport : apiClientService.getAirports()) {
            airports.put(airport.getIATA(), airport);
        }

        createGraph();

        CostPathPair<Integer> result = Dijkstra.getShortestPath(graph, vertices.get(start),
                vertices.get(end));

        return result;
    }


    private void createGraph() {
        loadVerticesAndEdges();
        graph = new Graph(Graph.TYPE.DIRECTED, vertices.values(), edges);
    }

    private void loadVerticesAndEdges() {
        for (Route route : routes) {
            Vertex vFrom = null;
            // fromCity
            if (vertices.containsKey(route.getFromAirport())) {
                vFrom = vertices.get(route.getFromAirport());
            } else {
                // add to vertices since it does not exists
                vFrom = new Vertex(route.getFromAirport());
                vertices.put(route.getFromAirport(), vFrom);
            }
            Vertex vTo = null;
            // toCity
            if (vertices.containsKey(route.getToAirport())) {
                vTo = vertices.get(route.getToAirport());
            } else {
                // add to vertices since it does not exists
                vTo = new Vertex(route.getToAirport());
                vertices.put(route.getToAirport(), vTo);
            }

            Airport from = airports.get(route.getFromAirport());
            Airport to = airports.get(route.getToAirport());
            if (to != null && from != null) {
                int cost = DistanceCalculator.calculateDistanceBetweenCoordinates(from.getLat(),
                        from.getLng(), to.getLat(), to.getLng());
                Edge e = new Edge(cost, vFrom, vTo);
                vFrom.addEdge(e);
                edges.add(e);
            } else {
                System.out.println("Invalid route " + route.toString());
            }
        }
    }
}
