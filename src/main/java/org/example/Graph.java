package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {
    private final Map<String, Vertex> vertices;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public Vertex getVertex(String vertex) {
        return vertices.get(vertex);
    }

    public void addVertex(String vertexName) {
        Vertex newVertex = new Vertex(vertexName);
        if (!vertices.containsKey(vertexName)) {
            vertices.put(vertexName, newVertex);
        }
    }

    public void addEdge(String source, String destination, int time, ArrayList<Constraints> constraints) {
        addVertex(source);
        addVertex(destination);
        Vertex sourceV = vertices.get(source);
        Vertex destinationV = vertices.get(destination);

        int newTime = checkConstraint(source, destination, constraints);
        if (newTime == 0)
            sourceV.addNeighbor(destinationV, time);
         else
            sourceV.addNeighbor(destinationV, newTime);
    }
    private Integer checkConstraint(String source, String destination, ArrayList<Constraints> constraints) {
        for(Constraints c : constraints) {
            if (c.matches(source, destination)) {
                double probability = c.getProbability();
                if (probability == 0.0) {
                    return 0;
                }
                double randomDouble = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
                if (randomDouble <= probability) {
                    return -1;
                }
            }
        }
        return 0;
    }
}

