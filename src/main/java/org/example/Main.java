package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Constraints> constraints = getConstraints();
        try{
        Graph fivePlacesGraph;
        fivePlacesGraph = createGraph("src/main/java/org/example/five_places.txt", constraints);
        Graph tenPlacesGraph;
        tenPlacesGraph = createGraph("src/main/java/org/example/ten_places.txt", constraints);
        Graph allPlacesAGraph;
        allPlacesAGraph = createGraph("src/main/java/org/example/all_places_a.txt", constraints);
        Graph allPlacesBGraph;
        allPlacesBGraph = createGraph("src/main/java/org/example/all_places_b.txt", constraints);
        Graph simpleGraph;
        simpleGraph = createGraph("src/main/java/org/example/simple.txt", constraints);
        Graph complexGraph;
        complexGraph = createGraph("src/main/java/org/example/complex.txt", constraints);

        output(fivePlacesGraph, "Five_places");
        output(tenPlacesGraph, "Ten_places");
        output(allPlacesAGraph, "All_places_A");
        output(allPlacesBGraph, "All_places_B");
        output(simpleGraph, "Simple");
        output(complexGraph, "Complex");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        File file = new File("src/main/java/org/example/places.txt");
        Scanner s = new Scanner(file);
        if (s.hasNextLine()) s.nextLine();

        try{
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] linePart = line.split(",");
                String shortcode = linePart[0].trim();
                String name = linePart[1].trim();

                Places.addPlace(shortcode, name);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    private static void output(Graph graph, String file) throws IOException {
        try (FileWriter output = new FileWriter(file + "_Results.txt")) {
            for (String source : graph.getVertices().keySet()) {
                String sourceName = Places.getPlaceName(source);

                for (String destination : graph.getVertices().keySet()) {
                    if (!source.equals(destination)) {
                        String destinationName = Places.getPlaceName(destination);
                        int time = Dijkstra.dijkstra(graph, source, destination);
                        String measure;
                        if(time>1)
                            measure = "seconds";
                        else
                            measure = "second";
                        output.write(String.format("%s -> %s: %d %s%n", sourceName, destinationName, time, measure));
                    }
                }
            }
        }
    }


    public static Graph createGraph(String file, ArrayList<Constraints> constraints) throws FileNotFoundException {
        Graph graph = new Graph();
        File f = new File(file);
        Scanner s = new Scanner(f);

        try {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] linePart = line.split(" ");
                String source = linePart[0].trim();
                String destination = linePart[1].trim();
                int time = Integer.parseInt(linePart[2].trim());
                graph.addEdge(source, destination, time, constraints);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return graph;
    }

    public static ArrayList<Constraints> getConstraints() throws FileNotFoundException {
        ArrayList<Constraints> constraints = new ArrayList<>();
        File file = new File("src/main/java/org/example/constraints.txt");
        Scanner s = new Scanner(file);
        if(s.hasNextLine())s.nextLine();

        try {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] linePart = line.split(",");
                String placeA = linePart[0].trim();
                String placeB = linePart[1].trim();
                Double probability = Double.parseDouble(linePart[3].trim());
                Constraints c = new Constraints(placeA, placeB, probability);
                constraints.add(c);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return constraints;
    }
}