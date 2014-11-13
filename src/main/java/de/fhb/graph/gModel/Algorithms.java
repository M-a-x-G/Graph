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

    public static void mstPrimAlgorithm(Graph graph) {
        if (graph.isWeightedGraph()) {
            FibonacciHeap<Vertex> fibonacciHeap = new FibonacciHeap<>();
            Iterator<Vertex> graphVerticesIterator = graph.getVertices().iterator();
            HashMap<Vertex, INode<Vertex>> nodeMap = new HashMap<>();
            Vertex root = graphVerticesIterator.next();
            nodeMap.put(root, fibonacciHeap.insert(0, root));

            while (graphVerticesIterator.hasNext()) {
                Vertex vertex = graphVerticesIterator.next();
                nodeMap.put(vertex, fibonacciHeap.insert(Integer.MAX_VALUE, vertex));
            }

            while (!fibonacciHeap.isEmpty()) {
                INode<Vertex> minVertex = fibonacciHeap.extractMin();
                for (Edge edge : graph.getEdgesOf(minVertex.value())) {
                    INode<Vertex> selectedVertex;
                    if (edge.getFrom().equals(minVertex.value())) {
                        selectedVertex = nodeMap.get(edge.getTo());
                    } else {
                        selectedVertex = nodeMap.get(edge.getFrom());
                    }
                    if (!fibonacciHeap.isExcluded(selectedVertex) && edge.getWeight() < selectedVertex.key()) {
                        fibonacciHeap.decreseKey(selectedVertex, edge.getWeight());
                        selectedVertex.value().setParent(minVertex.value());
                        System.out.println("\tfound min edge: " + edge.getFrom() + " to  " + edge.getTo() + " weight: " + edge.getWeight() + " selected key " + selectedVertex.key());
                    }
                }
            }

            for (Vertex vertex : nodeMap.keySet()) {
                Vertex parent = vertex.getParent();
                Iterator<Edge> edgeIterator = graph.getEdgesOf(vertex).iterator();
                boolean continueLoop = true;
                while (continueLoop && edgeIterator.hasNext()) {
                    Edge nextEdge = edgeIterator.next();
                    if (parent != null && (nextEdge.getTo().equals(parent) || nextEdge.getFrom().equals(parent))) {
                        nextEdge.setFat(true);
                        continueLoop = false;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Bad idea to use mstPrimAlgorithm without weights");
        }
    }


    public static HashSet<Edge> mstKruskalAlgorithm(Graph g){

        HashSet<HashSet<Vertex>> setOfComponents = new HashSet<>(g.getVertices().size());
        HashSet<Edge> result = new HashSet<>();

        //Make up the priority queue and setup the comparator
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {

                if(o1.getWeight() > o2.getWeight()){
                    return 1;
                } else if(o1.getWeight() < o2.getWeight()){
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
            HashSet<Vertex> tempSet = new HashSet<>();
            tempSet.add(vert);
            setOfComponents.add(tempSet);
        }
        int initialQueueSize = queue.size();
        for(int i = 0; i < initialQueueSize && !queue.isEmpty(); i++){
            Edge currentEdge = queue.poll();
            HashSet<Vertex> set1 = findSet(setOfComponents, currentEdge.getTo());
            HashSet<Vertex> set2 = findSet(setOfComponents, currentEdge.getFrom());

            if(set1 != set2){
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
        // the algorithm shall return the set of edges he assumes the safest.
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
