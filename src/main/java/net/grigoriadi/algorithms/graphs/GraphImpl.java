package net.grigoriadi.algorithms.graphs;

import java.util.*;

public class GraphImpl<T> implements Graph<T> {

    private final Map<T, List<T>> graph;

    public GraphImpl(int count) {
        graph = new HashMap<>(count);
    }

    public void addConnection(T a, T b) {
        List<T> aConns = getBucket(a);
        List<T> bConns = getBucket(b);
        if (!aConns.contains(b)) {
            aConns.add(b);
        }
        if (!bConns.contains(a)) {
            bConns.add(a);
        }
    }

    public List<T> getConnectedVertices(T a) {
        return getBucket(a);
    }

    @Override
    public int getCount() {
        return graph.keySet().size();
    }

    private List<T> getBucket(T a) {
        return graph.computeIfAbsent(a, k -> new ArrayList<>());
    }

    @Override
    public Set<T> getVertices() {
        return graph.keySet();
    }
}
