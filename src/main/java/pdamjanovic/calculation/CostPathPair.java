package pdamjanovic.calculation;

import java.util.Iterator;
import java.util.List;

public class CostPathPair<T extends Comparable<T>> {

    private int cost = 0;
    private List<Edge> path = null;

    public CostPathPair(int cost, List<Edge> path) {
        if (path == null)
            throw (new NullPointerException("path cannot be NULL."));

        this.cost = cost;
        this.path = path;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Edge> getPath() {
        return path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = this.cost;
        for (Edge e : path)
            hash *= e.getCost();
        return 31 * hash;
    }

    /**
     * {@inheritDoc}
     */
	@Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CostPathPair))
            return false;

        final CostPathPair<?> pair = (CostPathPair<?>) obj;
        if (this.cost != pair.cost)
            return false;

        final Iterator<?> iter1 = this.getPath().iterator();
        final Iterator<?> iter2 = pair.getPath().iterator();
        while (iter1.hasNext() && iter2.hasNext()) {
            Edge e1 = (Edge) iter1.next();
            Edge e2 = (Edge) iter2.next();
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
        builder.append("Cost = ").append(cost).append("\n");
        for (Edge e : path)
            builder.append("\t").append(e);
        return builder.toString();
    }
}