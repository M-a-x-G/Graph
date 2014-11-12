package de.fhb.graph.gModel;

import java.util.HashMap;
import java.util.HashSet;

public class AdjacencyList extends HashMap<Vertex,HashSet<Edge>> {


	private static final long serialVersionUID = 1L;
	
	public void addEdge(Edge e){
		Vertex from = e.getfrom();
		Vertex to = e.getTo();
		HashSet<Edge> neighbours;
		neighbours = (containsKey(from) ? get(from) : new HashSet<Edge>());
		neighbours.add(e);
		this.put(from, neighbours);
		if(from.getEdges() == null){
			from.setEdges(neighbours);
		}
		neighbours = (containsKey(to) ? get(to) : new HashSet<Edge>());
		neighbours.add(e);
		this.put(to, neighbours);
		if(to.getEdges() == null){
			from.setEdges(neighbours);
		}
	}
	
	public void removeEdge(Edge e){
		get(e.getfrom()).remove(e);
		get(e.getTo()).remove(e);
	}
	
	public HashSet<Vertex> getNeighboursOf(Vertex v){
	    HashSet<Vertex> result = new HashSet<Vertex>();

        if(get(v) != null){
		    for (Edge e : get(v)){
			    if(e.getfrom().equals(v) && !result.contains(e.getTo())){
                    result.add(e.getTo());
                }
			    if(e.getTo().equals(v) && !result.contains(e.getfrom())){
                    result.add(e.getfrom());
                }
		    }
        }

	    return result;
	}

	public HashSet<Edge> getEdgesOf(Vertex v){
		return get(v);
	}

}
