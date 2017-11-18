package pdamjanovic.calculation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Vertex implements Comparable<Vertex> {

	private String value = null;
	private int weight = 0;
	private List<Edge> edges = new ArrayList<Edge>();

	public Vertex(String value) {
		this.value = value;
	}

	public Vertex(String value, int weight) {
		this(value);
		this.weight = weight;
	}

	/** Deep copies the edges along with the value and weight **/
	public Vertex(Vertex vertex) {
		this(vertex.value, vertex.weight);

		this.edges.addAll(vertex.edges);
	}

	public String getValue() {
		return value;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void addEdge(Edge e) {
		edges.add(e);
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public Edge getEdge(Vertex v) {
		for (Edge e : edges) {
			if (e.getToVertex().equals(v))
				return e;
		}
		return null;
	}

	public boolean pathTo(Vertex v) {
		for (Edge e : edges) {
			if (e.getToVertex().equals(v))
				return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int code = this.value.hashCode() + this.weight + this.edges.size();
		return 31 * code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object v1) {
		if (!(v1 instanceof Vertex))
			return false;

		final Vertex v = (Vertex) v1;

		final boolean weightEquals = this.weight == v.weight;
		if (!weightEquals)
			return false;

		final boolean edgesSizeEquals = this.edges.size() == v.edges.size();
		if (!edgesSizeEquals)
			return false;

		final boolean valuesEquals = this.value.equals(v.value);
		if (!valuesEquals)
			return false;

		final Iterator<Edge> iter1 = this.edges.iterator();
		final Iterator<Edge> iter2 = v.edges.iterator();
		while (iter1.hasNext() && iter2.hasNext()) {
			// Only checking the cost
			final Edge e1 = iter1.next();
			final Edge e2 = iter2.next();
			if (e1.getCost() != e2.getCost())
				return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Vertex v) {
		final int valueComp = this.value.compareTo(v.value);
		if (valueComp != 0)
			return valueComp;

		if (this.weight < v.weight)
			return -1;
		if (this.weight > v.weight)
			return 1;

		if (this.edges.size() < v.edges.size())
			return -1;
		if (this.edges.size() > v.edges.size())
			return 1;

		final Iterator<Edge> iter1 = this.edges.iterator();
		final Iterator<Edge> iter2 = v.edges.iterator();
		while (iter1.hasNext() && iter2.hasNext()) {
			// Only checking the cost
			final Edge e1 = iter1.next();
			final Edge e2 = iter2.next();
			if (e1.getCost() < e2.getCost())
				return -1;
			if (e1.getCost() > e2.getCost())
				return 1;
		}

		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Value=").append(value).append(" weight=").append(weight).append("\n");
		for (Edge e : edges)
			builder.append("\t").append(e.toString());
		return builder.toString();
	}
}
