package net.grigoriadi.algorithms.graphs;

import java.util.*;

public class DepthFirstSearch<T> implements Search<T> {

    private final Set<T> marks;

    private final Graph<T> graph;

    private final Map<T, T> edgeTo;

    private final T s;

    public DepthFirstSearch(Graph<T> graph, T s) {
        this.graph = graph;
        marks = new HashSet<>(graph.getCount());
        edgeTo = new HashMap<>(graph.getCount());
        this.s = s;
        dfs(s);
    }

    public void dfs(T v) {
        System.out.println("Visiting: " + v);
        marks.add(v);
        List<T> connectedVertices = graph.getConnectedVertices(v);
        connectedVertices.stream().filter(w->!marks.contains(w)).forEach(w -> {
            edgeTo.put(w, v);
            dfs(w);
        });
    }

    @Override
    public Stack<T> pathTo(T v) {
        Stack<T> path = new Stack<>();
        for (T s = v; s != null; s = edgeTo.get(s)) {
            path.push(s);
        }
        return path;
    }

    public Set<T> getMarks() {
        return marks;
    }
}
