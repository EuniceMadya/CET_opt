package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;
import util.IntArray;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(CompressedGraph graph, boolean saveToMem) {
        super(graph, saveToMem);
        traversalType = TraversalType.BFS;
    }


    // BFS traversal
    public void traversal(int start) {

        ArrayQueue<IntArray> queue = new ArrayQueue<>(graph.getNumVertex());

        IntArray path = new IntArray(1);
        path.add(start);

        queue.offer(path);
        if (graph.endContains(start)) {
            validPaths.add((path.getArray()));
            pathNum++;
            return;
        }
        while (!queue.isEmpty()) {
            IntArray currentPath = queue.poll();
            int cur = currentPath.getLast();
            for (int i = graph.rowIndex[cur]; i < graph.rowIndex[cur + 1]; i++) {
                int neighbour = graph.colIndex[i];

                IntArray newStack = new IntArray(currentPath);
                newStack.add(neighbour);
                if (graph.endContains(neighbour)) {
                    if (saveToMem) validPaths.add(newStack.getArray());
                    pathNum++;
                } else queue.offer(newStack);
            }
            currentPath = null;
        }
    }
}
