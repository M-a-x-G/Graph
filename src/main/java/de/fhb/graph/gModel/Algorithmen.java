package de.fhb.graph.gModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;


public class Algorithmen {
	
	static int colour = 0;
	
	public static void findComponents(Graph g){
		colour = colour+1;
		Stack<Vertex> agenda = new Stack<Vertex>();
		HashSet<Vertex> vertices = g.getVertices();
		if (vertices.isEmpty()) return;
		Iterator<Vertex> it = vertices.iterator();
		Vertex first = it.next();
		while (first.isMarked()) {
			if (!it.hasNext()) return;
			first = it.next();
		}
		agenda.push(first);
		while (!agenda.isEmpty()){
			Vertex actual = agenda.pop();
			if (!actual.isMarked()){
				actual.setMarked(true);
				actual.setColour(colour);
				for (Vertex v : actual.getNeighbours()){
					if (!agenda.contains(v)) agenda.push(v);
				}
			}
		}


	}

}
