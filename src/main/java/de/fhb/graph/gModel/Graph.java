package de.fhb.graph.gModel;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;

public class Graph extends Observable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private HashSet<Vertex> vertices;
	private HashSet<Edge> edges;
	private AdjacencyList adjacencyList;
	private int actualVertexNumber;
	private boolean changed;
	private boolean weightedGraph;


    public Graph() {
		super();
		this.vertices = new HashSet<>();
		this.edges = new HashSet<>();
		this.adjacencyList = new AdjacencyList();
		actualVertexNumber = 0;
		changed = false;
        weightedGraph = false;
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
		HashSet<Edge> toRemove = new HashSet<>();
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

    /**
     * returns all vertices of this graph
     */
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

	public void mstKruskalAlgorithm(){
		Algorithmen.findSpanningTreeKruskal(this);
		setChanged();
		notifyObservers();

	}

	public void mstPrimAlgorith(){
		Algorithmen.mstPrimAlgorithm(this);
		setChanged();
		notifyObservers();
	}

	public void resetNonPersistentProps(){
		for (Vertex v : vertices){
            v.resetNonPersistentProps();
        }
		for (Edge e : edges){
            e.resetNonPersistentProps();
        }
		setChanged();
		notifyObservers();		
	}
	
	protected void changed(){
		setChanged();
		notifyObservers();		
	}

    public void setWeightedGraph(boolean weightedGraph) {
        this.weightedGraph = weightedGraph;
        setChanged();
        notifyObservers();
    }

    public boolean isWeightedGraph() {
        return weightedGraph;
    }


}
