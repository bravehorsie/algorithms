import net.grigoriadi.algorithms.graphs.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class GraphTest {

    @Test
    public void testDepthFirstSearch() {
        DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(buildSimpleComponentGraph(), 0);
    }

    @Test
    public void testConnected() {
        Graph<Integer> graph = new GraphImpl<>(100);
        graph.addConnection(0,1);
        graph.addConnection(1,2);
        graph.addConnection(1,3);
        graph.addConnection(2,4);
        graph.addConnection(2,5);
        graph.addConnection(3,6);
        graph.addConnection(3,7);
        graph.addConnection(3,4);
        graph.addConnection(7,8);

        graph.addConnection(10, 11);
        graph.addConnection(10, 12);
        graph.addConnection(12, 13);
        graph.addConnection(12, 14);
        graph.addConnection(13, 15);
        graph.addConnection(13, 16);
        graph.addConnection(12, 16);

        ConnectedComponents<Integer> connectedComponents = new ConnectedComponents<>(graph);
        Assert.assertTrue(connectedComponents.connected(0, 8));
        Assert.assertTrue(connectedComponents.connected(1, 8));
        Assert.assertTrue(connectedComponents.connected(3, 0));
        Assert.assertTrue(connectedComponents.connected(7, 1));

        Assert.assertTrue(connectedComponents.connected(10, 16));
        Assert.assertTrue(connectedComponents.connected(12, 13));
        Assert.assertTrue(connectedComponents.connected(14, 10));
        Assert.assertTrue(connectedComponents.connected(16, 13));

        Assert.assertFalse(connectedComponents.connected(1,10));
        Assert.assertFalse(connectedComponents.connected(0,16));
        Assert.assertFalse(connectedComponents.connected(8,12));
        Assert.assertFalse(connectedComponents.connected(3,16));
        Assert.assertFalse(connectedComponents.connected(20,30));
    }

    @Test
    public void testBfsFindShortestPath() {
        BreadthFirstSearch<Integer> dfs = new BreadthFirstSearch<>(buildSimpleComponentGraph(), 0);
        assertStackElements(dfs.pathTo(5), new Integer[]{0,5});
        assertStackElements(dfs.pathTo(1), new Integer[]{0,1});
        assertStackElements(dfs.pathTo(4), new Integer[]{0,2,4});
        assertStackElements(dfs.pathTo(3), new Integer[]{0,2,3});
    }

    /**
     * Path results of DFS depends heavily on order in which elements are added to the graph.
     */
    @Test
    public void testDfsFindAnyPath() {
        DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(buildSimpleComponentGraph(), 0);

        assertStackElements(dfs.pathTo(5), new Integer[]{0,2,3,5});
        assertStackElements(dfs.pathTo(1), new Integer[]{0,2,1});
        assertStackElements(dfs.pathTo(4), new Integer[]{0,2,3,4});
        assertStackElements(dfs.pathTo(3), new Integer[]{0,2,3});
    }

    private <T> void assertStackElements(Stack<T> stack, T[] elements) {
        int i = 0;
        while (!stack.isEmpty()) {
            T element = stack.pop();
            Assert.assertEquals(elements[i], element);
            i++;
        }
    }

    private void printPath(Search<Integer> dfs, Integer v) {
        System.out.print("Path to " + v +": ");
        dfs.pathTo(v).forEach(w-> System.out.print(w + ", "));
        System.out.println();
    }

    private Graph<Integer> buildSimpleComponentGraph() {
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
