package pdamjanovic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pdamjanovic.calculation.CostPathPair;
import pdamjanovic.calculation.Edge;
import pdamjanovic.calculation.Vertex;
import pdamjanovic.model.Airport;
import pdamjanovic.service.ApiClientService;
import pdamjanovic.service.RouteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by p.damjanovic on 11/18/2017.
 */
@Controller
public class RoutesController {

    @Autowired
    ApiClientService apiClientService;

    @Autowired
    RouteService routeService;
    Map<String, Airport> airports;

    @PostMapping("/routes")
    public String findRoute(@RequestParam String from, @RequestParam String to, ModelMap modelMap) throws IOException {
        airports = new HashMap<>();
        for(Airport airport : apiClientService.getAirports()) {
            airports.put(airport.getIATA(), airport);
        }

        CostPathPair<Integer> result = routeService.findRoute(airports, from, to);

        if (result != null) {
            int cost = result.getCost();
            modelMap.put("cost", cost);

            List<Edge> edges = result.getPath();

            StringBuilder routeDesc = new StringBuilder("Route description:<br/>");

            List<Airport> routeAirports = new ArrayList<>();

            boolean firstAdded = false;
            for (Edge edge : edges) {
                int partCost = edge.getCost();

                Vertex partFrom = edge.getFromVertex();
                String fromIata = partFrom.getValue();
                Airport fromAirport = airports.get(fromIata);
                if (!firstAdded) {
                    routeAirports.add(fromAirport);
                    firstAdded = true;
                }
                Vertex partTo = edge.getToVertex();
                String toIata = partTo.getValue();
                Airport toAirport = airports.get(toIata);

                routeAirports.add(toAirport);

                routeDesc.append(edge.toString()).append("<br/>");
            }

            Airport[] airportsArray = new Airport[routeAirports.size()];
            routeAirports.toArray(airportsArray);
            modelMap.put("airportsArray", airportsArray);
            modelMap.put("routeDesc", routeDesc.toString());
        } else {
            modelMap.put("routeDesc", "Route does not exist");
        }
        /*
        modelMap.put("routeDesc", "Brisbane --> San Francisco");

        Airport airport1 = new Airport(44.8184013367, 20.3090991974);
        Airport airport2 = new Airport(43.82460021972656, 18.331499099731445);
        Airport airport3 = new Airport(44.8184013367, 20.3090991974);
        Airport airport4 = new Airport(47.436901092499994, 19.255599975599996);

        Airport[] airportsArray = {airport1, airport2, airport3, airport4};
        modelMap.put("airportsArray", airportsArray);
        */

        return "routes_result";
    }

    @GetMapping("/routes")
    public String getRoutesPage() throws IOException {
        return "routes";
    }

}
