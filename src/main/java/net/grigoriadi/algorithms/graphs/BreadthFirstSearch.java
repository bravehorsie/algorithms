package net.grigoriadi.algorithms.graphs;

import java.util.*;

public class BreadthFirstSearch<T> implements Search<T> {

    private final Graph<T> graph;

    private final T start;

    private final Set<T> visited;

    private final Map<T, T> edgeTo;

    private final Deque<T> toVisit;

    public BreadthFirstSearch(Graph<T> graph, T start) {
        this.graph = graph;
        this.start = start;
        this.visited = new HashSet<>();
        this.edgeTo = new HashMap<>();
        toVisit = new ArrayDeque<>();
        toVisit.add(start);
        visited.add(start);
        bfs();
    }

    public void bfs() {
        T v;
        while ((v = toVisit.pollFirst()) != null) {
            System.out.println("Visiting: " + v);
            List<T> connectedVertices = graph.getConnectedVertices(v);
            enqueueVertices(v, connectedVertices);
        }

    }

    private void enqueueVertices(T v, List<T> connectedVertices) {
        connectedVertices.stream().filter(w->!visited.contains(w)).forEach(w -> {
            edgeTo.put(w, v);
            visited.add(w);
            toVisit.addLast(w);
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
}
