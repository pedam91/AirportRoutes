package pdamjanovic.calculation;

import java.util.*;

/**
 * Dijkstra's shortest path. Only works on non-negative path weights. Returns a
 * tuple of total cost of shortest path and the path.
 * <p>
 * Worst case: O(|E| + |V| log |V|)
 * <p>
 *
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">Dijkstra's
 *      Algorithm (Wikipedia)</a> <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Dijkstra {

    private Dijkstra() {
    }

    public static Map<Vertex, CostPathPair<Integer>> getShortestPaths(Graph graph, Vertex start) {
        final Map<Vertex, List<Edge>> paths = new HashMap<Vertex, List<Edge>>();
        final Map<Vertex, CostVertexPair> costs = new HashMap<Vertex, CostVertexPair>();

        getShortestPath(graph, start, null, paths, costs);

        final Map<Vertex, CostPathPair<Integer>> map = new HashMap<Vertex, CostPathPair<Integer>>();
        for (CostVertexPair pair : costs.values()) {
            int cost = pair.getCost();
            Vertex vertex = pair.getVertex();
            List<Edge> path = paths.get(vertex);
            map.put(vertex, new CostPathPair<Integer>(cost, path));
        }
        return map;
    }

    public static CostPathPair<Integer> getShortestPath(Graph graph, Vertex start, Vertex end) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));

        // Dijkstra's algorithm only works on positive cost graphs
        final boolean hasNegativeEdge = checkForNegativeEdges(graph.getVertices());
        if (hasNegativeEdge)
            throw (new IllegalArgumentException("Negative cost Edges are not allowed."));

        final Map<Vertex, List<Edge>> paths = new HashMap<Vertex, List<Edge>>();
        final Map<Vertex, CostVertexPair> costs = new HashMap<Vertex, CostVertexPair>();
        return getShortestPath(graph, start, end, paths, costs);
    }

    private static CostPathPair<Integer> getShortestPath(Graph graph, Vertex start, Vertex end,
                                                         Map<Vertex, List<Edge>> paths, Map<Vertex, CostVertexPair> costs) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));
        if (start == null)
            throw (new NullPointerException("start must be non-NULL."));

        // Dijkstra's algorithm only works on positive cost graphs
        boolean hasNegativeEdge = checkForNegativeEdges(graph.getVertices());
        if (hasNegativeEdge)
            throw (new IllegalArgumentException("Negative cost Edges are not allowed."));

        for (Vertex v : graph.getVertices())
            paths.put(v, new ArrayList<Edge>());

        for (Vertex v : graph.getVertices()) {
            if (v.equals(start))
                costs.put(v, new CostVertexPair(0, v));
            else
                costs.put(v, new CostVertexPair(Integer.MAX_VALUE, v));
        }

        final Queue<CostVertexPair> unvisited = new PriorityQueue<CostVertexPair>();
        unvisited.add(costs.get(start));

        while (!unvisited.isEmpty()) {
            final CostVertexPair pair = unvisited.remove();
            final Vertex vertex = pair.getVertex();

            // Compute costs from current vertex to all reachable vertices which haven't
            // been visited
            for (Edge e : vertex.getEdges()) {
                final CostVertexPair toPair = costs.get(e.getToVertex()); // O(1)
                final CostVertexPair lowestCostToThisVertex = costs.get(vertex); // O(1)
                final int cost = lowestCostToThisVertex.getCost() + e.getCost();
                if (toPair.getCost() == Integer.MAX_VALUE) {
                    // Haven't seen this vertex yet

                    // Need to remove the pair and re-insert, so the priority queue keeps it's
                    // invariants
                    unvisited.remove(toPair); // O(n)
                    toPair.setCost(cost);
                    unvisited.add(toPair); // O(log n)

                    // Update the paths
                    List<Edge> set = paths.get(e.getToVertex()); // O(log n)
                    set.addAll(paths.get(e.getFromVertex())); // O(log n)
                    set.add(e);
                } else if (cost < toPair.getCost()) {
                    // Found a shorter path to a reachable vertex

                    // Need to remove the pair and re-insert, so the priority queue keeps it's
                    // invariants
                    unvisited.remove(toPair); // O(n)
                    toPair.setCost(cost);
                    unvisited.add(toPair); // O(log n)

                    // Update the paths
                    List<Edge> set = paths.get(e.getToVertex()); // O(log n)
                    set.clear();
                    set.addAll(paths.get(e.getFromVertex())); // O(log n)
                    set.add(e);
                }
            }

            // Termination conditions
            if (end != null && vertex.equals(end)) {
                // We are looking for shortest path to a specific vertex, we found it.
                break;
            }
        }

        if (end != null) {
            final CostVertexPair pair = costs.get(end);
            final List<Edge> set = paths.get(end);
            return (new CostPathPair<Integer>(pair.getCost(), set));
        }
        return null;
    }

    private static boolean checkForNegativeEdges(Collection<Vertex> vertitices) {
        for (Vertex v : vertitices) {
            for (Edge e : v.getEdges()) {
                if (e.getCost() < 0)
                    return true;
            }
        }
        return false;
    }
}
