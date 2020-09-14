package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;

import java.sql.Timestamp;
import java.util.Stack;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(CompressedGraph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.BFS;
    }

    // BFS traversal
    public void traversal(int start) {

        ArrayQueue<Stack<Integer>> queue = new ArrayQueue<>(graph.getNumVertex());


        Stack<Integer> path = new Stack<>();
        path.add(start);

        queue.offer(path);
        while (!queue.isEmpty()) {
            Stack<Integer> currentPath = queue.poll();
            int cur = currentPath.peek();
            for (int i = graph.rowIndex[cur]; i < graph.rowIndex[cur + 1]; i++) {
                int neighbour = graph.colIndex[i];

                Stack<Integer> newStack = new Stack<>();

                newStack.addAll(currentPath);
                newStack.push(neighbour);
                if (graph.getNumDegree(neighbour) == 0) {
//                    validPaths.add(new ArrayList<>(newStack);
                    validPaths.add(getPath(newStack));
                    pathNum ++;
                } else queue.offer(newStack);
            }
        }
    }
}
