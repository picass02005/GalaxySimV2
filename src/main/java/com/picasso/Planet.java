package com.picasso;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

/**
 * Class used to represent a planet and calculate positions after each steps
 *
 * @author picasso2005
 */
public class Planet {
    private final double G = (6.6743 * pow(10, -11));  // TODO: define a factor in config file

    public Double x;
    public Double y;
    public Vector velocity;
    public final int mass;
    public final Color color;

    /**
     * Class constructor, must be only called by builder
     *
     * @param builder Builder class used to pass every parameter to this class
     */
    private Planet(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.velocity = builder.velocity;
        this.mass = builder.mass;
        this.color = builder.color;
    }

    public static void calculateNewStep(List<Planet> planets) {
        for (Planet planet : planets) {
            planet.calculateNewVelocity(planets);
        }

        for (Planet planet : planets) {
            planet.calculateNewPosition();
        }
    }

    /**
     * Internal method to calculate a half-vector using 2 point (A and B) half-coordinate (X or Y),
     * AB distance and a vector length
     *
     * @param a      A point half coordinate (can be X or Y value)
     * @param b      B point half coordinate (can be X or Y, must be the same as A)
     * @param ab     AB length
     * @param length Returned vector length
     * @return The half coordinates of the vector calculated (either X or Y)
     */
    private double calcHalfCoordinate(Double a, Double b, Double ab, Double length) {
        return (1 / ab) * abs(length) * (b - a);
    }

    /**
     * Apply current velocity to planet to calculate new position
     */
    private void calculateNewPosition() {
        this.x += this.velocity.x;
        this.y += this.velocity.y;
    }

    /**
     * Calculate gravitational acceleration and add it to current velocity
     *
     * @param planets List of all planets in simulation
     */
    private void calculateNewVelocity(List<Planet> planets) {
        Vector accelVector = new Vector(0, 0);

        for (Planet planet : planets) {
            if (planet != this) {
                // d = sqrt(x^2 + y^2)
                double distance = sqrt(pow(this.x - planet.x, 2) + pow(this.y - planet.y, 2));

                // F = G * ((m1 * m2) / d^2)
                // a = F / m
                double gravAccel = (this.G * ((this.mass * planet.mass) / pow(distance, 2))) / this.mass;

                accelVector.addVector(new Vector(
                        calcHalfCoordinate(this.x, planet.x, distance, gravAccel),
                        calcHalfCoordinate(this.y, planet.y, distance, gravAccel)
                ));
            }
        }

        this.velocity.addVector(accelVector);
    }

    /**
     * Builder class made to generate all parameters for Planet class
     *
     * @author picasso2005
     */
    public static class Builder {
        private Double x;
        private Double y;
        private Vector velocity = null;
        private Integer mass = 0;
        private Color color = null;

        /**
         * Define this planet positions at start of simulation
         *
         * @param x The desired x coordinate
         * @param y The desired y coordinate
         * @return Builder with this parameter updated
         */
        public Builder position(Double x, Double y) {
            this.x = x;
            this.y = y;

            return this;
        }

        /**
         * Define this planet velocity at start of simulation
         *
         * @param velocity A vector representing desired velocity
         * @return Builder with this parameter updated
         */
        public Builder velocity(Vector velocity) {
            this.velocity = velocity;

            return this;
        }

        /**
         * Define this planet mass for the simulation
         * This can't be changed once the simulation is started
         *
         * @param mass The planet desired mass
         * @return Builder with this parameter updated
         */
        public Builder mass(Integer mass) {
            this.mass = mass;

            return this;
        }

        /**
         * Define this planet color for the simulation
         * This can't be changed once the simulation is started
         *
         * @param color The planet desired color
         * @return Builder with this parameter updated
         */
        public Builder color(Color color) {
            this.color = color;

            return this;
        }

        /**
         * Build method that generate a planet, setting any unset values to random ones according to config
         *
         * @return The generated planet class
         */
        public Planet build() {
            final Random rand = new Random();

            if (this.x == null) {
                final int max_x = 1920; // TODO: config file
                this.x = rand.nextDouble(max_x * 2) - max_x;
            }

            if (this.y == null) {
                final int max_y = 1080; // TODO: config file
                this.y = rand.nextDouble(max_y * 2) - max_y;
            }

            if (this.velocity == null) {
                final float max = 10;  // TODO: config file
                this.velocity = new Vector(rand.nextFloat(-max, max), rand.nextFloat(-max, max));
            }

            if (this.mass == null) {
                mass = rand.nextInt(1, 20);  // TODO: config file
            }

            if (this.color == null) {
                this.color = new Color(rand.nextInt(0x1000000));
            }

            return new Planet(this);
        }
    }
}
