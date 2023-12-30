package org.example;

import java.util.*;

public class Dijkstra {
    public static int dijkstra(Graph graph, String startVertex, String endvertex) {
        Map<String, Integer> distances = new HashMap<>();
        ArrayList<Distance> vertices = new ArrayList<>();
        distances.put(startVertex, 0);
        int initialDistance = distances.get(startVertex);
        Distance d = new Distance(graph.getVertex(startVertex), initialDistance);
        vertices.add(d);

        while (!vertices.isEmpty()) {
            int minIndex = getMinIndex(vertices);
            Distance current = vertices.remove(minIndex);
            Vertex currentVertex = current.getVertex();
            int currentDistance = current.getDistance();

            if (currentVertex != null) {
                if (currentVertex.toString().contentEquals(endvertex)) {
                    return currentDistance;
                }

                for (Map.Entry<Vertex, Integer> e : currentVertex.getNeighbors().entrySet()) {
                    Vertex neighborVertex = e.getKey(); int distanceToNeighbor = e.getValue();

                    if (distanceToNeighbor < 0) {
                        continue;
                    }
                    int newDistance = currentDistance + e.getValue();

                    if (!distances.containsKey(neighborVertex.toString()) || newDistance < distances.get(neighborVertex.toString())) {
                        distances.put(neighborVertex.toString(), newDistance);
                        vertices.add(new Distance(neighborVertex, newDistance));
                    }
                }
            }
        }
        return -1;
    }

    private static int getMinIndex(ArrayList<Distance> vertices) {
        int minIndex = 0;
        int minDistance = Integer.MAX_VALUE;

        int currentIndex = 0;
        for (Distance distance : vertices) {
            int currentDistance = distance.getDistance();
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                minIndex = currentIndex;
            }
            currentIndex++;
        }

        return minIndex;
    }
}
