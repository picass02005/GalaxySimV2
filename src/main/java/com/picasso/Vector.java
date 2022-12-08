package com.picasso;

/**
 * Class used to define a vector coordinates and add multiple vectors
 *
 * @author picasso2005
 */
public class Vector {
    double x;
    double y;

    /**
     * Class constructor to create a vector associated with 2 coordinates
     *
     * @param x The x-axis coordinates component (works with float or double)
     * @param y The y-axis coordinates component (works with float or double)
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Static method made to add 2 vectors together
     *
     * @param vector A vector to add to the current one
     * @return The resulting vector
     */
    public Vector addVector(Vector vector) {
        this.x += vector.x;
        this.y += vector.y;

        return this;
    }
}
