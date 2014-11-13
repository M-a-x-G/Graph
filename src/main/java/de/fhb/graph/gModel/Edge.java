package de.fhb.graph.gModel;

import java.io.Serializable;

public class Edge implements Serializable {


	private static final long serialVersionUID = 1L;
	private Vertex from;
	private Vertex to;
	private int weight;
	private int color;
	private boolean marker;
	private boolean fat;
	private Graph graph;
	
	public Edge(Vertex left, Vertex right, int weight, Graph graph) {
		super();
		this.from = left;
		this.to = right;
		this.weight = weight;
		this.graph = graph;
		marker = false;
	}

	public Edge(Vertex left, Vertex right, Graph graph) {
		super();
		this.from = left;
		this.to = right;
		this.weight = 0;
		this.graph = graph;
		marker = false;
	}

	public Vertex getFrom() {
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
		graph.changed();
	}
	
	protected boolean meets(Vertex v){
		return (from == v || to == v);
	}

	public void resetNonPersistentProps(){
		marker = false;
		fat = false;
	}

	public boolean isMarker() {
		return marker;
	}

	public void setMarker(boolean marker) {
		this.marker = marker;
		graph.changed();
	}

	public boolean isFat() {
		return fat;
	}

	public void setFat(boolean fat) {
		this.fat = fat;
		graph.changed();
	}
}
