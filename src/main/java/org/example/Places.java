package org.example;

import java.util.HashMap;
import java.util.Map;

public class Places {
    private static final Map<String, String> places = new HashMap<>();

    public static void addPlace(String shortcode, String name) {
        places.put(shortcode, name);
    }

    public static String getPlaceName(String shortcode) {
        String result = places.get(shortcode);
        if (result != null) {
            return result;
        } else {
            return shortcode;
        }

    }

}
