package de.fhb.graph.gModel;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;


public class Algorithmen {

    static int colour = 0;

    public static void findComponents(Graph g) {
        colour = 0;
        Stack<Vertex> agenda = new Stack<Vertex>();
        HashSet<Vertex> vertices = g.getVertices();

        if (vertices.isEmpty()) {
            return;
        }

        Iterator<Vertex> it = vertices.iterator();
        Vertex first;

        while (it.hasNext()) {
            first = it.next();
            if (!first.isMarked()) {
                agenda.push(first);
                colour += 1;
                System.out.println("Colour: " + colour);
                while (!agenda.isEmpty()) {
                    Vertex actual = agenda.pop();
                    if (!actual.isMarked()) {
                        actual.setMarked(true);
                        actual.setColour(colour);
                        for (Vertex v : actual.getNeighbours()) {
                            if (!agenda.contains(v)) {
                                agenda.push(v);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void mstPrimAlgorithm(Graph graph) {
//        for (Vertex vertex : graph.getVertices()) {
//            System.out.println(vertex.getName());
//            if (vertex.getEdges() != null) {
//                for (Edge edge : vertex.getEdges()) {
//                    System.out.println("\tFrom: " + edge.getfrom() + "\tto: " + edge.getTo());
//                }
//            } else {
//                System.out.println("Edges are null");
//            }
//        }
        if (graph.isWeightedGraph()) {

            PriorityQueue<Vertex> queue = new PriorityQueue<>(new Comparator<Vertex>() {
                @Override
                public int compare(Vertex v1, Vertex v2) {
                    if (v1.getKey() > v2.getKey()) {
                        return 1;
                    } else if (v1.getKey() < v2.getKey()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            Iterator<Vertex> graphVerticesIterator = graph.getVertices().iterator();
            HashSet<Vertex> extractedVertices = new HashSet<>(graph.getVertices().size());
            Vertex root = graphVerticesIterator.next();
            root.setKey(0);
            queue.add(root);
            while (graphVerticesIterator.hasNext()) {
                Vertex vertex = graphVerticesIterator.next();
                vertex.setKey(Integer.MAX_VALUE);
                queue.add(vertex);
            }

            while (!queue.isEmpty()) {
                Vertex minVertex = queue.poll();
                extractedVertices.add(minVertex);
                System.out.println("min vertex: " + minVertex + " key " + minVertex.getKey());
                for (Edge edge : graph.getEdgesOf(minVertex)) {
                    Vertex selectedVertex;
                    if (edge.getfrom().equals(minVertex)) {
                        selectedVertex = edge.getTo();
                    } else {
                        selectedVertex = edge.getfrom();
                    }
                    if (queue.contains(selectedVertex) && edge.getWeight() < selectedVertex.getKey()) {
                        selectedVertex.setKey(edge.getWeight());
                        selectedVertex.setParent(minVertex);
                        System.out.println("\tfound min edge: " + edge.getfrom() + " to  " + edge.getTo() + " weight: " + edge.getWeight() + " selected key " + selectedVertex.getKey());
                    }
                }
            }

            for (Vertex vertex : extractedVertices) {
                Vertex parent = vertex.getParent();
                Iterator<Edge> edgeIterator = graph.getEdgesOf(vertex).iterator();
                boolean continueLoop = true;
                while (continueLoop && edgeIterator.hasNext()) {
                    Edge nextEdge = edgeIterator.next();
                    if (parent != null && (nextEdge.getTo().equals(parent) || nextEdge.getfrom().equals(parent))) {
                        nextEdge.setFat(true);
                        continueLoop = false;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Bad idea to use mstPrimAlgorithm without weights");
        }
    }
    public static HashSet<Edge> findSpanningTreeKruskal(Graph g){

        HashSet<HashSet<Vertex>> setOfComponents = new HashSet<>(g.getVertices().size());
        HashSet<Edge> result = new HashSet<>();

        //Make up the priority queue and setup the comparator
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {


                if(o1.getWeight() > o1.getWeight()){
                    return 1;
                } else if(o1.getWeight() < o1.getWeight()){
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        // add all edges of the current graph to the sorted queue
        queue.addAll(g.getEdges());

        //create a component for every Vertex there is in the graph as this is the initial state of the algorithm.
        for(Vertex vert : g.getVertices()){
            HashSet<Vertex> tempSet = new HashSet<Vertex>();
            tempSet.add(vert);
            setOfComponents.add(tempSet);
        }

        for(int i = 0; i < queue.size() && !queue.isEmpty(); i++){
            Edge currentEdge = queue.poll();
            HashSet<Vertex> set1 = findSet(setOfComponents, currentEdge.getTo());
            HashSet<Vertex> set2 = findSet(setOfComponents, currentEdge.getfrom());

            if(set1 != set2){
                set1.addAll(set2);
                result.add(currentEdge);
                setOfComponents.remove(set2);
            }
        }
        return result;

    }


    private static HashSet<Vertex> findSet(HashSet<HashSet<Vertex>> setOfComponents, Vertex vertex){

        for(HashSet<Vertex> verts : setOfComponents){
            if(verts.contains(vertex)){
                return verts;
            }
        }

        return null;
    }
}
