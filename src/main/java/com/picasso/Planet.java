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
    public final int mass;
    public final Color color;
    private final double G = (6.6743 * pow(10, -11));
    public Float x;
    public Float y;
    public Vector velocity;

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

    private double calcHalfCoordinate(float a, float b, double ab, double acceleration) {
        return 1 / ab * abs(acceleration) * (b - a) + a;
    }

    private void calculateNewVelocity(List<Planet> planets) {
        Vector accelVector = new Vector(0, 0);

        for (Planet planet : planets) {
            if (planet != this) {
                // d = sqrt(x^2 + y^2)
                double distance = sqrt(pow(this.x - planet.x, 2) + pow(this.y - planet.y, 2));

                // F = G * ((m1 * m2) / d^2)
                // a = F / m
                double gravAccel = this.mass / (this.G * ((this.mass * planet.mass) / pow(distance, 2)));

                accelVector.addVector(new Vector(
                        calcHalfCoordinate(this.x, planet.x, distance, gravAccel),
                        calcHalfCoordinate(this.y, planet.y, distance, gravAccel)
                ));
            }
        }

        this.velocity.addVector(accelVector);
    }

    /**
     * Builder class made to generate all parameters for Planet class if user don't give them
     *
     * @author picasso2005
     */
    public static class Builder {
        private Float x;
        private Float y;
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
        public Builder position(Float x, Float y) {
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
                this.x = rand.nextFloat(max_x * 2) - max_x;
            }

            if (this.y == null) {
                final int max_y = 1080; // TODO: config file
                this.y = rand.nextFloat(max_y * 2) - max_y;
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
