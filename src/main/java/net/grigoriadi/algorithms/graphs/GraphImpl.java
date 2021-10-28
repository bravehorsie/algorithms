package net.grigoriadi.algorithms.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphImpl<T> implements Graph<T> {

    private final Map<T, List<T>> graph;

    private final int V;

    public GraphImpl(int V) {
        graph = new HashMap<T, List<T>>(V);
        this.V = V;
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
    public int getV() {
        return V;
    }

    private List<T> getBucket(T a) {
        List<T> bucket = graph.get(a);
        if (bucket == null) {
            bucket = new ArrayList<T>();
            graph.put(a, bucket);
        }
        return bucket;
    }
}
