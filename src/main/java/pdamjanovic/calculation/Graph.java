package pdamjanovic.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Graph. Could be directed or undirected depending on the TYPE enum. A graph is
 * an abstract representation of a set of objects where some pairs of the
 * objects are connected by links.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Graph_(mathematics)">Graph (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Graph {

    private List<Vertex> allVertices = new ArrayList<Vertex>();
    private List<Edge> allEdges = new ArrayList<Edge>();

    public enum TYPE {
        DIRECTED, UNDIRECTED
    }

    /** Defaulted to undirected */
    private TYPE type = TYPE.DIRECTED;

    public Graph() { }

    public Graph(TYPE type) {
        this.type = type;
    }

    /** Deep copies **/
    public Graph(Graph g) {
        type = g.getType();

        // Copy the vertices which also copies the edges
        for (Vertex v : g.getVertices())
            this.allVertices.add(new Vertex(v));

        for (Vertex v : this.getVertices()) {
            for (Edge e : v.getEdges()) {
                this.allEdges.add(e);
            }
        }
    }

    /**
     * Creates a Graph from the vertices and edges. This defaults to an undirected Graph
     * 
     * NOTE: Duplicate vertices and edges ARE allowed.
     * NOTE: Copies the vertex and edge objects but does NOT store the Collection parameters itself.
     * 
     * @param vertices Collection of vertices
     * @param edges Collection of edges
     */
    public Graph(Collection<Vertex> vertices, Collection<Edge> edges) {
        this(TYPE.DIRECTED, vertices, edges);
    }

    /**
     * Creates a Graph from the vertices and edges.
     * 
     * NOTE: Duplicate vertices and edges ARE allowed.
     * NOTE: Copies the vertex and edge objects but does NOT store the Collection parameters itself.
     * 
     * @param vertices Collection of vertices
     * @param edges Collection of edges
     */
    public Graph(TYPE type, Collection<Vertex> vertices, Collection<Edge> edges) {
        this(type);

        this.allVertices.addAll(vertices);
        this.allEdges.addAll(edges);

        for (Edge e : edges) {
            final Vertex from = e.getFromVertex();
            final Vertex to = e.getToVertex();

            if (!this.allVertices.contains(from) || !this.allVertices.contains(to))
                continue;

            from.addEdge(e);
            if (this.type == TYPE.UNDIRECTED) {
                Edge reciprical = new Edge(e.getCost(), to, from);
                to.addEdge(reciprical);
                this.allEdges.add(reciprical);
            }
        }
    }

    public TYPE getType() {
        return type;
    }

    public List<Vertex> getVertices() {
        return allVertices;
    }

    public List<Edge> getEdges() {
        return allEdges;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int code = this.type.hashCode() + this.allVertices.size() + this.allEdges.size();
        for (Vertex v : allVertices)
            code *= v.hashCode();
        for (Edge e : allEdges)
            code *= e.hashCode();
        return 31 * code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object g1) {
        if (!(g1 instanceof Graph))
            return false;

        final Graph g = (Graph) g1;

        final boolean typeEquals = this.type == g.type;
        if (!typeEquals)
            return false;

        final boolean verticesSizeEquals = this.allVertices.size() == g.allVertices.size();
        if (!verticesSizeEquals)
            return false;

        final boolean edgesSizeEquals = this.allEdges.size() == g.allEdges.size();
        if (!edgesSizeEquals)
            return false;

        // Vertices can contain duplicates and appear in different order but both arrays should contain the same elements
        final Object[] ov1 = this.allVertices.toArray();
        Arrays.sort(ov1);
        final Object[] ov2 = g.allVertices.toArray();
        Arrays.sort(ov2);
        for (int i=0; i<ov1.length; i++) {
            final Vertex v1 = (Vertex) ov1[i];
            final Vertex v2 = (Vertex) ov2[i];
            if (!v1.equals(v2))
                return false;
        }

        // Edges can contain duplicates and appear in different order but both arrays should contain the same elements
        final Object[] oe1 = this.allEdges.toArray();
        Arrays.sort(oe1);
        final Object[] oe2 = g.allEdges.toArray();
        Arrays.sort(oe2);
        for (int i=0; i<oe1.length; i++) {
            final Edge e1 = (Edge) oe1[i];
            final Edge e2 = (Edge) oe2[i];
            if (!e1.equals(e2))
                return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Vertex v : allVertices)
            builder.append(v.toString());
        return builder.toString();
    }
}