package net.grigoriadi.algorithms.graphs;

import java.util.List;
import java.util.Set;

/**
 * Graph data structure.
 * None thread safe.
 * @param <T>
 */
public interface Graph<T> {

    /**
     * Connect two vertices.
     */
    void addConnection(T a, T b);

    /**
     * Get connected vertices for a given vertex.
     */
    List<T> getConnectedVertices(T a);

    /**
     * Get count of vertices.
     * @return vertex count
     */
    int getCount();

    /**
     * Get of vertices in the graph unordered.
     */
    Set<T> getVertices();
}
