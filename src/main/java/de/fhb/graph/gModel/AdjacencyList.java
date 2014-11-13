package de.fhb.graph.gModel;

import java.util.HashMap;
import java.util.HashSet;

public class AdjacencyList extends HashMap<Vertex,HashSet<Edge>> {


	private static final long serialVersionUID = 1L;
	
	public void addEdge(Edge e){
		Vertex from = e.getFrom();
		Vertex to = e.getTo();
		HashSet<Edge> neighbours;
		neighbours = (containsKey(from) ? get(from) : new HashSet<>());
		neighbours.add(e);
		this.put(from, neighbours);
		from.setEdges(neighbours);
		neighbours = (containsKey(to) ? get(to) : new HashSet<>());
		neighbours.add(e);
		this.put(to, neighbours);
		to.setEdges(neighbours);
	}
	
	public void removeEdge(Edge e){
		get(e.getFrom()).remove(e);
		get(e.getTo()).remove(e);
	}
	
	public HashSet<Vertex> getNeighboursOf(Vertex v){
	    HashSet<Vertex> result = new HashSet<>();

        if(get(v) != null){
		    for (Edge e : get(v)){
			    if(e.getFrom().equals(v) && !result.contains(e.getTo())){
                    result.add(e.getTo());
                }
			    if(e.getTo().equals(v) && !result.contains(e.getFrom())){
                    result.add(e.getFrom());
                }
		    }
        }

	    return result;
	}

	public HashSet<Edge> getEdgesOf(Vertex v){
		return get(v);
	}

}
