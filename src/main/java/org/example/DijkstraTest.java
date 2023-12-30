package org.example;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DijkstraTest extends TestCase {

    private void fillPlaces() throws FileNotFoundException {
        File file = new File("src/main/java/org/example/places.txt");
        Scanner s = new Scanner(file);
        if (s.hasNextLine()) s.nextLine();

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] linePart = line.split(",");
            String shortcode = linePart[0].trim();
            String name = linePart[1].trim();

            Places.addPlace(shortcode, name);
        }
    }
    private ArrayList<Constraints> getConstraintsWithZeroWeight() {
        ArrayList<Constraints> constraints = new ArrayList<>();
        Constraints c = new Constraints("A", "B", 0.0);
        constraints.add(c);
        return constraints;
    }

    private ArrayList<Constraints> getConstraintsWithProbabilities() {
        ArrayList<Constraints> constraintsList = new ArrayList<>();
        constraintsList.add(new Constraints("A", "B", 1.0));
        constraintsList.add(new Constraints("B", "D", 1.0));
        constraintsList.add(new Constraints("B", "M", 0.5));
        constraintsList.add(new Constraints("C", "I", 1.0));
        constraintsList.add(new Constraints("G", "I", 1.0));
        return constraintsList;
    }

    public void testShortestPaths() throws FileNotFoundException {
        fillPlaces();
        ArrayList<Constraints> constraintsList = getConstraintsWithZeroWeight();
        Graph graph = buildGraphForPathsTest(constraintsList);

        assertEquals(-1, Dijkstra.dijkstra(graph, "A", "D"));
        assertEquals(10, Dijkstra.dijkstra(graph, "A", "B"));
        assertEquals(15, Dijkstra.dijkstra(graph, "B", "C"));
    }

    private Graph buildGraphForPathsTest(ArrayList<Constraints> constraints) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 10, constraints);
        graph.addEdge("B", "C", 15, constraints);
        graph.addEdge("E", "D", 20, constraints);
        return graph;
    }

    public void testShortestPathsBetweenConnectedPlaces() throws FileNotFoundException {
        fillPlaces();
        ArrayList<Constraints> constraints = getConstraintsWithZeroWeight();
        Graph graph = buildConnectedGraphForPathsTest(constraints);

        assertEquals(10, Dijkstra.dijkstra(graph, "A", "B"));
        assertEquals(25, Dijkstra.dijkstra(graph, "A", "C"));
        assertEquals(20, Dijkstra.dijkstra(graph, "A", "D"));
        assertEquals(30, Dijkstra.dijkstra(graph, "A", "E"));

        assertEquals(40, Dijkstra.dijkstra(graph, "B", "A"));
        assertEquals(15, Dijkstra.dijkstra(graph, "B", "C"));
        assertEquals(35, Dijkstra.dijkstra(graph, "B", "D"));
        assertEquals(20, Dijkstra.dijkstra(graph, "B", "E"));

        assertEquals(60, Dijkstra.dijkstra(graph, "C", "A"));
        assertEquals(70, Dijkstra.dijkstra(graph, "C", "B"));
        assertEquals(20, Dijkstra.dijkstra(graph, "C", "D"));
        assertEquals(40, Dijkstra.dijkstra(graph, "C", "E"));

        assertEquals(40, Dijkstra.dijkstra(graph, "D", "A"));
        assertEquals(50, Dijkstra.dijkstra(graph, "D", "B"));
        assertEquals(65, Dijkstra.dijkstra(graph, "D", "C"));
        assertEquals(20, Dijkstra.dijkstra(graph, "D", "E"));

        assertEquals(20, Dijkstra.dijkstra(graph, "E", "A"));
        assertEquals(30, Dijkstra.dijkstra(graph, "E", "B"));
        assertEquals(45, Dijkstra.dijkstra(graph, "E", "C"));
        assertEquals(20, Dijkstra.dijkstra(graph, "E", "D"));
    }

    private Graph buildConnectedGraphForPathsTest(ArrayList<Constraints> constraints) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 10, constraints);
        graph.addEdge("B", "C", 15, constraints);
        graph.addEdge("E", "D", 20, constraints);
        graph.addEdge("A", "D", 20, constraints);
        graph.addEdge("E", "A", 20, constraints);
        graph.addEdge("B", "E", 20, constraints);
        graph.addEdge("C", "D", 20, constraints);
        graph.addEdge("D", "E", 20, constraints);
        return graph;
    }

    public void testShortestPathWithInvalidSource() throws FileNotFoundException {
        fillPlaces();
        ArrayList<Constraints> constraintsList = getConstraintsWithZeroWeight();
        Graph graph = buildGraphForInvalidSourceTest(constraintsList);

        assertEquals(-1, Dijkstra.dijkstra(graph, "F", "D"));
    }

    private Graph buildGraphForInvalidSourceTest(ArrayList<Constraints> constraints) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 10, constraints);
        graph.addEdge("B", "C", 15, constraints);
        graph.addEdge("E", "D", 20, constraints);
        return graph;
    }

    public void testShortestPathWithInvalidDestination() throws FileNotFoundException {
        fillPlaces();
        ArrayList<Constraints> constraintsList = getConstraintsWithZeroWeight();
        Graph graph = buildGraphForInvalidDestinationTest(constraintsList);

        assertEquals(-1, Dijkstra.dijkstra(graph, "A", "F"));
    }

    private Graph buildGraphForInvalidDestinationTest(ArrayList<Constraints> constraints) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 10, constraints);
        graph.addEdge("B", "C", 15, constraints);
        graph.addEdge("E", "D", 20, constraints);
        return graph;
    }

    public void testShortestPathWithNegativeTime() throws FileNotFoundException {
        fillPlaces();
        ArrayList<Constraints> constraints = getConstraintsWithZeroWeight();
        Graph graph = graphWithNegativeTimeEdge(constraints);

        int shortestPathTime = Dijkstra.dijkstra(graph, "A", "D");

        assertEquals(-1, shortestPathTime);
    }

    private Graph graphWithNegativeTimeEdge(ArrayList<Constraints> constraintsList) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", -10, constraintsList);
        graph.addEdge("B", "C", 15, constraintsList);
        graph.addEdge("C", "D", 20, constraintsList);
        return graph;
    }


    public void testShortestPathWithSelfLoop() throws FileNotFoundException {
        fillPlaces();
        ArrayList<Constraints> constraints = getConstraintsWithZeroWeight();
        Graph graph = buildGraphWithSelfLoop(constraints);

        assertEquals(45, Dijkstra.dijkstra(graph, "A", "D"));
        assertEquals(0, Dijkstra.dijkstra(graph, "D", "D"));
    }

    private Graph buildGraphWithSelfLoop(ArrayList<Constraints> constraintcs) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 10, constraintcs);
        graph.addEdge("B", "C", 15, constraintcs);
        graph.addEdge("C", "D", 20, constraintcs);
        graph.addEdge("D", "D", -5, constraintcs);
        return graph;
    }


    public void testShortestPathsConsideringProbability() throws FileNotFoundException {
        fillPlaces();
        ArrayList<Constraints> constraints = getConstraintsWithProbabilities();
        Graph graph = buildGraphWithProbabilities(constraints);

        assertEquals(-1, Dijkstra.dijkstra(graph, "A", "B"));
        assertEquals(15, Dijkstra.dijkstra(graph, "B", "C"));
        assertEquals(-1, Dijkstra.dijkstra(graph, "B", "D"));
        assertEquals(-1, Dijkstra.dijkstra(graph, "C", "I"));
        assertEquals(-1, Dijkstra.dijkstra(graph, "A", "C"));
    }

    private Graph buildGraphWithProbabilities(ArrayList<Constraints> constraints) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 10, constraints);
        graph.addEdge("B", "C", 15, constraints);
        graph.addEdge("B", "D", 20, constraints);
        graph.addEdge("C", "I", 20, constraints);
        return graph;
    }


    public void testShortestPathsNoConstraints() throws FileNotFoundException {
        fillPlaces();
        ArrayList<Constraints> noConstraints = getConstraintsWithZeroWeight();
        Graph graph = buildGraphNoConstraints(noConstraints);

            assertEquals(10, Dijkstra.dijkstra(graph, "A", "B"));
        assertEquals(15, Dijkstra.dijkstra(graph, "B", "C"));
        assertEquals(20, Dijkstra.dijkstra(graph, "B", "D"));
        assertEquals(20, Dijkstra.dijkstra(graph, "C", "I"));
    }

    private Graph buildGraphNoConstraints(ArrayList<Constraints> constraints) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 10, constraints);
        graph.addEdge("B", "C", 15, constraints);
        graph.addEdge("B", "D", 20, constraints);
        graph.addEdge("C", "I", 20, constraints);
        return graph;
    }
}
