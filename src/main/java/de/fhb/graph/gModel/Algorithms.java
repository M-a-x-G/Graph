package de.fhb.graph.gModel;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

import de.fhb.graph.utility.FibonacciHeap;
import de.fhb.graph.utility.INode;


public class Algorithms {

    static int colour = 0;


    /**
     * Finds all components in a graph and recolors them.
     * When more then 10 components belong to the graph colors start to repeat.
     */
    public static void findComponents(Graph g) {
        colour = 0;
        Stack<Vertex> agenda = new Stack<>();
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

    /**
     * Finds the minimal spanning tree of a graph using Prims algorithm.
     * Sets the "fat" flag of the found edges thus causing them
     * to be drawn thicker than edges not belonging to the tree.
     */
    public static void mstPrimAlgorithm(Graph graph) throws IllegalArgumentException {
        if (!graph.isWeightedGraph()) {
            throw new IllegalArgumentException("Bad idea to use mstPrimAlgorithm without weights");
        }
        if (graph.getVertices().size() > 1) {
            FibonacciHeap<Vertex> fibonacciHeap = new FibonacciHeap<>();
            Iterator<Vertex> graphVerticesIterator = graph.getVertices().iterator();
            HashMap<Vertex, INode<Vertex>> nodeMap = new HashMap<>();
            Vertex startVertex = graphVerticesIterator.next();
            nodeMap.put(startVertex, fibonacciHeap.insert(0, startVertex));

            while (graphVerticesIterator.hasNext()) {
                Vertex vertex = graphVerticesIterator.next();
                nodeMap.put(vertex, fibonacciHeap.insert(Integer.MAX_VALUE, vertex));
            }

            while (!fibonacciHeap.isEmpty()) {
                INode<Vertex> minVertex = fibonacciHeap.extractMin();
                HashSet<Edge> edgesToNeighbors = graph.getEdgesOf(minVertex.value());
                if (edgesToNeighbors != null) {
                    for (Edge edge : edgesToNeighbors) {
                        INode<Vertex> selectedVertex;
                        selectedVertex = edge.getFrom().equals(minVertex.value()) ?
                                nodeMap.get(edge.getTo()) : nodeMap.get(edge.getFrom());
                        if (!fibonacciHeap.isExcluded(selectedVertex) && edge.getWeight() < selectedVertex.key()) {
                            fibonacciHeap.decreaseKey(selectedVertex, edge.getWeight());
                            selectedVertex.value().setParent(minVertex.value());
                        }
                    }
                }
            }

            //Paint the MST
            for (Vertex vertex : nodeMap.keySet()) {
                Vertex parent = vertex.getParent();
                HashSet<Edge> edgesToNeighbors = graph.getEdgesOf(vertex);
                Iterator<Edge> edgeIterator;
                if (edgesToNeighbors != null) {
                    edgeIterator = edgesToNeighbors.iterator();
                    boolean continueLoop = true;
                    while (continueLoop && edgeIterator.hasNext()) {
                        Edge nextEdge = edgeIterator.next();
                        if (parent != null && (nextEdge.getTo().equals(parent) || nextEdge.getFrom().equals(parent))) {
                            nextEdge.setFat(true);
                            continueLoop = false;
                        }
                    }
                }
            }
        }
    }


    /**
     * Finds the minimal spanning tree of a graph using Kruskals algorithm.
     * Returns all edges belonging to the found tree.
     * Sets the "fat" flag of the found edges thus causing them
     * to be drawn thicker than edges not belonging to the tree.
     */
    public static HashSet<Edge> mstKruskalAlgorithm(Graph g) throws IllegalArgumentException {

        if (!g.isWeightedGraph()) {
            throw new IllegalArgumentException("Trying to calculate the mst you are. On a unweighted graph a failure it is.");
        }

        HashSet<HashSet<Vertex>> setOfComponents = new HashSet<>(g.getVertices().size());
        HashSet<Edge> result = new HashSet<>();

        //Make up the priority queue and setup the comparator
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {

                if (edge1.getWeight() > edge2.getWeight()) {
                    return 1;
                } else if (edge1.getWeight() < edge2.getWeight()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        // add all edges of the current graph to the queue
        queue.addAll(g.getEdges());


        //create a component for every Vertex there is in the graph as this is the initial state of the algorithm.
        for (Vertex vertex : g.getVertices()) {
            HashSet<Vertex> tempSet = new HashSet<>();
            tempSet.add(vertex);
            setOfComponents.add(tempSet);
        }
        int initialQueueSize = queue.size();
        for (int i = 0; i < initialQueueSize && !queue.isEmpty(); i++) {
            Edge currentEdge = queue.poll();
            HashSet<Vertex> set1 = findSet(setOfComponents, currentEdge.getTo());
            HashSet<Vertex> set2 = findSet(setOfComponents, currentEdge.getFrom());

            if (set1 != set2) {
                // i am forced to delete set1 here and add it later again
                // as java duplicates it otherwise for an unknown reason.
                setOfComponents.remove(set1);
                setOfComponents.remove(set2);
                set1.addAll(set2);
                setOfComponents.add(set1);

                result.add(currentEdge);
                // set the chosen edges to be fat. So they shall be marked for the users sake.
                currentEdge.setFat(true);
            }
        }
        // the algorithm shall return the set of edges it assumes to be the safest.
        return result;

    }

    /**
     * In a set of sets this methods finds the set in which the specified vertex is stored.
     * If no set contains the vertex null is returned.
     */
    private static HashSet<Vertex> findSet(HashSet<HashSet<Vertex>> setOfComponents, Vertex vertex) {

        for (HashSet<Vertex> selectedVertex : setOfComponents) {
            if (selectedVertex.contains(vertex)) {
                return selectedVertex;
            }
        }

        return null;
    }
}
