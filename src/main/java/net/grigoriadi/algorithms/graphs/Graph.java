package net.grigoriadi.algorithms.graphs;

import java.util.List;

/**
 * Graph data structure.
 * None thread safe.
 * @param <T>
 */
public interface Graph<T> {

    void addConnection(T a, T b);

    List<T> getConnectedVertices(T a);

    int getV();
}
