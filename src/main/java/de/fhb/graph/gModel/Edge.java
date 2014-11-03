package de.fhb.graph.gModel;

import java.io.Serializable;

public class Edge implements Serializable {


	private static final long serialVersionUID = 1L;
	Vertex from;
	Vertex to;
	int weight;
    int color;
	boolean marker;
	Graph g;
	
	public Edge(Vertex left, Vertex right, int weight, Graph g) {
		super();
		this.from = left;
		this.to = right;
		this.weight = weight;
		this.g = g;
		marker = false;
	}

	public Edge(Vertex left, Vertex right, Graph g) {
		super();
		this.from = left;
		this.to = right;
		this.weight = 0;
		this.g = g;
		marker = false;
	}

	public Vertex getfrom() {
		return from;
	}

	public void setfrom(Vertex left) {
		this.from = left;
	}

	public Vertex getTo() {
		return to;
	}

	public void setTo(Vertex right) {
		this.to = right;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
		g.changed();
	}
	
	protected boolean meets(Vertex v){
		return (from == v || to == v);
	}

	public void resetNonPersistentProps(){
		marker = false;
	}

	public boolean isMarker() {
		return marker;
	}

	public void setMarker(boolean marker) {
		this.marker = marker;
		g.changed();
	}
	


}
