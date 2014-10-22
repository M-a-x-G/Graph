package de.fhb.graph.gModel;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;


public class Vertex implements Serializable{
	
	private static final long serialVersionUID = 1L;
	String name;
	Point location;
	boolean marked;
	int colour;
	Graph g;
	
	public Vertex(String name, Point location, Graph g) {
		super();
		this.name = name;
		this.location = location;
		this.g = g;
		this.marked = false;
		this.colour = 0;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	public void incColour() {
		this.colour = colour+1;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
		System.out.println("Knoten: "+this);
		for (Vertex v : this.getNeighbours()){
			System.out.println(v);
		}
		
		g.changed();
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
		g.changed();
	}
	
	public void resetNonPersistentProps(){
		marked = false;
		colour = 0;
	}
	
	public HashSet<Vertex> getNeighbours(){
		return  g.getNeighboursOf(this);
	
	}
	
	


}
