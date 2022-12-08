package com.picasso;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.pow;

/**
 * Class used to represent a planet and calculate positions after each steps
 *
 * @author picasso2005
 */
public class Planet {
    public final int mass;
    public final Color color;
    private final double G = (6.6743 * pow(10, -11));
    public int x;
    public int y;
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

    /**
     * Builder class made to generate all parameters for Planet class if user don't give them
     *
     * @author picasso2005
     */
    public static class Builder {
        private int x = 0;
        private int y = 0;
        private Vector velocity = null;
        private int mass = 0;
        private Color color = null;

        /**
         * Define this planet positions at start of simulation
         *
         * @param x The desired x coordinate
         * @param y The desired y coordinate
         * @return Builder with this parameter updated
         */
        public Builder position(int x, int y) {
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
        public Builder mass(int mass) {
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

            if (this.x == 0) {
                final int max_x = 1920; // TODO: config file
                this.x = rand.nextInt(max_x * 2) - max_x;
            }

            if (this.y == 0) {
                final int max_y = 1080; // TODO: config file
                this.y = rand.nextInt(max_y * 2) - max_y;
            }

            if (this.velocity == null) {
                final float max = 10;  // TODO: config file
                this.velocity = new Vector(rand.nextFloat(-max, max), rand.nextFloat(-max, max));
            }

            if (this.mass == 0) {
                mass = rand.nextInt(1, 20);  // TODO: config file
            }

            if (this.color == null) {
                this.color = new Color(rand.nextInt(0x1000000));
            }

            return new Planet(this);
        }
    }
}
