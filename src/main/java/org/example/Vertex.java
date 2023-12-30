package org.example;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private final String vertexName;
    private final Map<Vertex, Integer> neighbors;

    public Vertex(String name) {
        this.vertexName = name;
        this.neighbors = new HashMap<>();
    }

    public Map<Vertex, Integer> getNeighbors() {
        return neighbors;
    }
    public void addNeighbor(Vertex neighbor, int time) {
        neighbors.put(neighbor, time);
    }

    @Override
    public String toString() {
        return vertexName;
    }
}
