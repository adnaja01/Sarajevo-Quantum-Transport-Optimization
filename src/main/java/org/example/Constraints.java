package org.example;

public class Constraints {
    private final String placeA;
    private final String placeB;
    private final Double probability;

    public Constraints(String placeA, String placeB, Double probability) {
        this.placeA = placeA;
        this.placeB = placeB;
        this.probability = probability;
    }

    public String getPlaceA() {
        return placeA;
    }

    public String getPlaceB() {
        return placeB;
    }

    public Double getProbability() {
        return probability;
    }

    public boolean matches(String source, String destination) {
        return placeA.equals(source) && placeB.equals(destination);
    }
}
