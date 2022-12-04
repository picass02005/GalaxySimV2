package com.picasso;

import java.util.Hashtable;
import java.util.List;

/**
 * Class used to define a vector coordinates and add multiple vectors
 *
 * @author picasso2005
 */
public class Vector {
    Hashtable<Character, Float> position = new Hashtable<>();

    /**
     * Class constructor to create a vector associated with 2 coordinates
     *
     * @param x The x-axis coordinates component
     * @param y The y-axis coordinates component
     */
    public Vector(float x, float y) {
        this.position.put('x', x);
        this.position.put('y', y);
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
            addedVectors.position.replace('x', addedVectors.position.get('x') + vector.position.get('x'));
            addedVectors.position.replace('y', addedVectors.position.get('y') + vector.position.get('y'));
        }

        return addedVectors;
    }
}
