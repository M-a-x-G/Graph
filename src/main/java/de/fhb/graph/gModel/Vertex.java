package de.fhb.graph.gModel;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;


public class Vertex implements Serializable, Comparable<Vertex> {

    private static final long serialVersionUID = 1L;
    private String name;
    private Point location;
    private boolean marked;
    private int colour;
    private Graph graph;
    private HashSet<Edge> edges;
    private int key;
    private Vertex parent;

    public Vertex(String name, Point location, Graph graph) {
        super();
        this.name = name;
        this.location = location;
        this.graph = graph;
        this.marked = false;
        this.colour = 0;
        this.edges = null;
    }

    public void addEdge(Edge edge) {
        if (edges != null) {
            edges.add(edge);
        } else {
            throw new NullPointerException("The EdgeHashSet in this vertex was not initialized");
        }
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public void incColour() {
        this.colour = colour + 1;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
        System.out.println("Knoten: " + this);
        for (Vertex v : this.getNeighbours()) {
            System.out.println(v);
        }

        graph.changed();
    }

    public String getName() {
        return name;
    }

    public String toString() {
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
        graph.changed();
    }

    public void resetNonPersistentProps() {
        marked = false;
        colour = 0;
    }

    public HashSet<Vertex> getNeighbours() {
        return graph.getNeighboursOf(this);

    }

    public HashSet<Edge> getEdges() {
        return edges;
    }


    public void setEdges(HashSet<Edge> edges) {
        if (edges != null && edges.size() > 0) {
            this.edges = edges;
        } else {
            throw new IllegalArgumentException("This setter is not for initialisation");
        }
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Vertex input) {
//        1 -> This > input
//        0 -> this == input
//        -1 -> this < iput
        if (getKey() > input.getKey()) {
            return 1;
        } else if (getKey() < input.getKey()) {
            return -1;
        } else {
            return 0;
        }
    }
}
