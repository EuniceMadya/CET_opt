package Traversal;

import Components.Graph;
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
            for (int neighbour : graph.getEdges(currentPath.peek())) {
                Stack <Integer> newStack = new Stack<>();
                newStack.addAll(currentPath);
                newStack.push(neighbour);

                if (graph.getVertex(neighbour).getNeighbours().size() == 0) {
                    identifyPattern(new ArrayList<>(newStack));
                    validPaths.add(new ArrayList<>(newStack));
                } else queue.offer(newStack);
            }
        }
    }
}
