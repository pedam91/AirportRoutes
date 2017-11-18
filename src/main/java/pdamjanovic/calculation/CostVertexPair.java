package pdamjanovic.calculation;


public class CostVertexPair implements Comparable<CostVertexPair> {

    private int cost = Integer.MAX_VALUE;
    private Vertex vertex = null;

    public CostVertexPair(int cost, Vertex vertex) {
        if (vertex == null)
            throw (new NullPointerException("vertex cannot be NULL."));

        this.cost = cost;
        this.vertex = vertex;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Vertex getVertex() {
        return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return 31 * (this.cost * ((this.vertex!=null)?this.vertex.hashCode():1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object e1) {
        if (!(e1 instanceof CostVertexPair))
            return false;

        final CostVertexPair pair = (CostVertexPair)e1;
        if (this.cost != pair.cost)
            return false;

        if (!this.vertex.equals(pair.vertex))
            return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(CostVertexPair p) {
        if (p == null)
            throw new NullPointerException("CostVertexPair 'p' must be non-NULL.");

        if (this.cost < p.cost)
            return -1;
        if (this.cost > p.cost)
            return 1;
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(vertex.getValue()).append(" (").append(vertex.getWeight()).append(") ").append(" cost=").append(cost).append("\n");
        return builder.toString();
    }
}