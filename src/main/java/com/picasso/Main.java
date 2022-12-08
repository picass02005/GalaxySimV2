package com.picasso;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Planet> planets = new ArrayList<>();

        planets.add(new Planet.Builder().mass(5000).position(5D, 1D).velocity(new Vector(0, 0)).build());
        planets.add(new Planet.Builder().mass(200).position(0D, 0D).velocity(new Vector(0, 0)).build());

        for (int i = 0; i < 5; i++) {
            for (Planet planet : planets) {
                System.out.println(planet.x + " " + planet.y);
            }
            Planet.calculateNewStep(planets);
        }
    }
}

// TODO: calculate it in parallel to increase calculation speed
// TODO: find a way to calculate faster for high speed simulation
// TODO: config file