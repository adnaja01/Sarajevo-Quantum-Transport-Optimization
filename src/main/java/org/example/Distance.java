package org.example;

public class Distance {
    private final Vertex vertex;
    private final int distance;

    public Distance(Vertex vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }
}

