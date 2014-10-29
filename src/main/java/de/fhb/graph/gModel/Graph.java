package de.fhb.graph.gModel;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;

public class Graph extends Observable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	HashSet<Vertex> vertices;
	HashSet<Edge> edges;
	AdjacencyList adjacencyList;
	int actualVertexNumber;
	boolean changed;

	public Graph() {
		super();
		this.vertices = new HashSet<Vertex>();
		this.edges = new HashSet<Edge>();
		this.adjacencyList = new AdjacencyList();
		actualVertexNumber = 0;
		changed = false;
	}
	

	public Vertex addVertex(Point location){
		Vertex v = new Vertex("v"+actualVertexNumber++,location, this);
		vertices.add(v);
		setChanged();
		notifyObservers();
		return v;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}


	public boolean isChanged() {
		return changed;
	}


	protected void setChanged(){
		super.setChanged();
		changed = true;
	}
	

    public void deleteGraph(){
        vertices.clear();
        edges.clear();
        setChanged();
        notifyObservers();
    }

	public void removeVertex(Vertex v){
		vertices.remove(v);
		HashSet<Edge> toRemove = new HashSet<Edge>();
		for (Edge e : edges)
			if (e.meets(v)){
				toRemove.add(e);
				adjacencyList.removeEdge(e);
			}	
		edges.removeAll(toRemove);
		setChanged();
		notifyObservers();
	}
	
	public void removeEdge(Edge e){
		edges.remove(e);
		adjacencyList.removeEdge(e);
		setChanged();
		notifyObservers();
	}
	
	public Edge addEdge(Vertex from, Vertex to){
		Edge e = new Edge(from, to, this);
		edges.add(e);
		adjacencyList.addEdge(e);
		setChanged();
		notifyObservers();
		return e;
	}


	public HashSet<Vertex> getVertices() {
		return vertices;
	}


	public HashSet<Edge> getEdges() {
		return edges;
	}
	
	public HashSet<Vertex> getNeighboursOf(Vertex v){
		return adjacencyList.getNeighboursOf(v);
	}
	
	public void findComponents(){
		Algorithmen.findComponents(this);
		setChanged(); 
		notifyObservers();		
		
	}
	
	public void resetNonPersistentProps(){
		for (Vertex v : vertices) v.resetNonPersistentProps();
		for (Edge e : edges) e.resetNonPersistentProps();
		setChanged();
		notifyObservers();		
	}
	
	protected void changed(){
		setChanged();
		notifyObservers();		
	}




}
