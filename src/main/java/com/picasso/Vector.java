package com.picasso;

import java.util.List;

/**
 * Class used to define a vector coordinates and add multiple vectors
 *
 * @author picasso2005
 */
public class Vector {
    float x;
    float y;

    /**
     * Class constructor to create a vector associated with 2 coordinates
     *
     * @param x The x-axis coordinates component
     * @param y The y-axis coordinates component
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Static method made to add 2 or more vectors together
     *
     * @param vectors A list of Vector object to add together
     * @return The resulting vector
     */
    public static Vector addVectors(List<Vector> vectors) {
        Vector addedVectors = new Vector(0, 0);

        for (Vector vector : vectors) {
            addedVectors.x = addedVectors.x + vector.x;
            addedVectors.y = addedVectors.y + vector.y;
        }

        return addedVectors;
    }
}
