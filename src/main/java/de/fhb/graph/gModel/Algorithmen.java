package de.fhb.graph.gModel;

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
        if (graph.isWeightedGraph()) {
            PriorityQueue<Vertex> queue = new PriorityQueue<>();
            Iterator<Vertex> graphVerticesIterator = graph.getVertices().iterator();
            Vertex root = graphVerticesIterator.next();
            root.setKey(0);
            queue.add(root);
            for (Vertex vertex = graphVerticesIterator.next(); graphVerticesIterator.hasNext(); vertex = graphVerticesIterator.next()) {
                vertex.setKey(Integer.MAX_VALUE);
                queue.add(vertex);
            }

            while (!queue.isEmpty()) {
                Edge minEdge = null;
                Vertex minVertex = queue.poll();
                for (Edge edge : minVertex.getEdges()) {
                    Vertex selectedVertex;
                    if (edge.getfrom().equals(minVertex)) {
                        selectedVertex = edge.getTo();
                    } else {
                        selectedVertex = edge.getfrom();
                    }
                    if (queue.contains(selectedVertex) && edge.getWeight() < selectedVertex.getKey()) {
                        minEdge = edge;
                        selectedVertex.setKey(edge.getWeight());
                        selectedVertex.setParent(minVertex);
                    }
                }
                if (minEdge != null) {
                    minEdge.setFat(true);
                }

            }
        }else{
            throw new IllegalArgumentException("Bad idea to use this without weights");
        }
    }
}
