package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;

import java.util.LinkedList;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(CompressedGraph graph, boolean saveToMem) {
        super(graph, saveToMem);
        traversalType = TraversalType.BFS;
    }


    // BFS traversal
    public void traversal(int start) {

        ArrayQueue<LinkedList<Integer>> queue = new ArrayQueue<>(graph.getNumVertex());

        LinkedList<Integer> path = new LinkedList<>();
        path.add(start);

        queue.offer(path);
        if (graph.getNumDegree(start) == 0) {
            validPaths.add(getPath(path));
            pathNum++;
            return;
        }
        while (!queue.isEmpty()) {
            LinkedList<Integer> currentPath = queue.poll();
            int cur = currentPath.peek();
            for (int i = graph.rowIndex[cur]; i < graph.rowIndex[cur + 1]; i++) {
                int neighbour = graph.colIndex[i];

                LinkedList<Integer> newStack = new LinkedList<>();
                newStack.addAll(currentPath);
                newStack.push(neighbour);
                if (graph.endContains(neighbour)) {
                    if (saveToMem) validPaths.add(getPath(newStack));
                    pathNum++;
                } else queue.offer(newStack);
            }
        }
    }
}
