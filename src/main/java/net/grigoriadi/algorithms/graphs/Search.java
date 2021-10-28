package net.grigoriadi.algorithms.graphs;

import java.util.Stack;

public interface Search<T> {

    Stack<T> pathTo(T s);
}
