import net.grigoriadi.algorithms.graphs.*;
import org.junit.Test;

public class GraphTest {

    @Test
    public void testDepthFirstSearch() {
        DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(buildGraph(), 0);
    }

    @Test
    public void testDfsFindPath() {
        DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(buildGraph(), 0);
        printPath(dfs,5);
        printPath(dfs,1);
        printPath(dfs,4);
        printPath(dfs,3);
    }

    @Test
    public void testBfsFindPath() {
        BreadthFirstSearch<Integer> bfs = new BreadthFirstSearch<>(buildGraph(), 0);
        printPath(bfs,5);
        printPath(bfs,1);
        printPath(bfs,4);
        printPath(bfs,3);
    }

    private void printPath(Search<Integer> dfs, Integer v) {
        System.out.print("Path to " + v +": ");
        dfs.pathTo(v).forEach(w-> System.out.print(w + ", "));
        System.out.println();
    }

    private Graph<Integer> buildGraph() {
        Graph<Integer> g = new GraphImpl<>(10);
        g.addConnection(0, 2);
        g.addConnection(3, 5);
        g.addConnection(3, 4);
        g.addConnection(0, 1);
        g.addConnection(1, 2);
        g.addConnection(2, 3);
        g.addConnection(2, 4);
        g.addConnection(0, 5);
        return g;
    }
}
