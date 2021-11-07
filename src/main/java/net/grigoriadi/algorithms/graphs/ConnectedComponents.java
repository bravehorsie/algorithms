package net.grigoriadi.algorithms.graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Examines a graph for the components (isolated not-connected sub-graphs), and supports
 * a query if two of given vertices are connected.
 *
 * @param <T> Subject
 */
public class ConnectedComponents<T> {

    private Graph<T> graph;

    /**
     * Maps each vertex to its sub-graph name.
     */
    private Map<T, Integer> vertexToComponent;

    /**
     * simple count serving as a name of a connected sub-graph.
     */
    private int componentName;

    private List<DepthFirstSearch<T>> searches;

    public ConnectedComponents(Graph<T> graph) {
        this.graph = graph;
        this.vertexToComponent = new HashMap<>();
        build();
    }

    private void build() {
        graph.getVertices().stream()
                .filter(v-> !vertexToComponent.containsKey(v))
                .forEach(v->{
                    DepthFirstSearch<T> search = new DepthFirstSearch<>(graph, v);
                    search.getMarks().forEach(m-> vertexToComponent.put(m, componentName));
                    componentName++;
                });
    }

    /**
     * True if provided vertices are connected.
     */
    public boolean connected(T a, T b) {
        Integer aComponent = vertexToComponent.get(a);
        Integer bComponent = vertexToComponent.get(b);
        if (aComponent == null || bComponent == null) {
            return false;
        }
        return aComponent.equals(bComponent);
    }
}
