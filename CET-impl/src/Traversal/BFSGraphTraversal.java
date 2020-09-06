package Traversal;

import Components.*;
import util.ArrayQueue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Stack;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.BFS;
    }

    // BFS traversal
    public void traversal(int start) {

        ArrayQueue<Stack<Integer>> queue = new ArrayQueue();

        Stack<Integer> path = new Stack<>();
        path.add(start);
        queue.offer(path);
        while (!queue.isEmpty()) {
            Stack<Integer> currentPath = queue.poll();
            for (int neighbour : graph.getNeighbours(currentPath.peek())) {
                Stack<Integer> newStack = new Stack<>();
                newStack.addAll(currentPath);
                newStack.push(neighbour);

                if (graph.getNeighbours(neighbour).size() == 0) {
                    validPaths.add(new ArrayList<>(newStack));
                } else queue.offer(newStack);
            }
        }
    }
}
