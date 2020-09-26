package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;

import java.util.Stack;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(CompressedGraph graph, boolean saveToMem) {
        super(graph, saveToMem);
        traversalType = TraversalType.BFS;
    }


    // BFS traversal
    public void traversal(int start) {

        ArrayQueue<Stack<Integer>> queue = new ArrayQueue<>(graph.getNumVertex());

        Stack<Integer> path = new Stack<>();
        path.push(start);

        queue.offer(path);
        if (graph.endContains(start)) {
            validPaths.add(getPath(path));
            pathNum++;
            return;
        }
        while (!queue.isEmpty()) {
            Stack<Integer> currentPath = queue.poll();
            int cur = currentPath.peek();
            for (int i = graph.rowIndex[cur]; i < graph.rowIndex[cur + 1]; i++) {
                int neighbour = graph.colIndex[i];

                Stack <Integer> newStack = new Stack<>();
                newStack.addAll(currentPath);
                newStack.push(neighbour);
                if (graph.endContains(neighbour)) {
                    if (saveToMem) validPaths.add(getPath(newStack));
                    pathNum++;
                } else queue.offer(newStack);
            }
            currentPath = null;
        }
    }
}
