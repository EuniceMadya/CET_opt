package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;

import java.util.ArrayList;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(CompressedGraph graph, boolean saveToMem) {
        super(graph, saveToMem);
        traversalType = TraversalType.BFS;
    }


    // BFS traversal
    public void traversal(int start) {

        ArrayQueue<ArrayList<Integer>> queue = new ArrayQueue<>(graph.getNumVertex());

        ArrayList<Integer> path = new ArrayList<>();
        path.add(start);

        queue.offer(path);
        if (graph.endContains(start)) {
            validPaths.add(getPath(path));
            pathNum++;
            return;
        }
        while (!queue.isEmpty()) {
            ArrayList<Integer> currentPath = queue.poll();
            int cur = currentPath.get(0);
            for (int i = graph.rowIndex[cur]; i < graph.rowIndex[cur + 1]; i++) {
                int neighbour = graph.colIndex[i];

                ArrayList <Integer> newStack = new ArrayList<>();
                newStack.addAll(currentPath);
                newStack.add(neighbour);
                if (graph.endContains(neighbour)) {
                    if (saveToMem) validPaths.add(getPath(newStack));
                    pathNum++;
                } else queue.offer(newStack);
            }
            currentPath = null;
        }
    }
}
