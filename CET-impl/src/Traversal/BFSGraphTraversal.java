package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;

import java.util.Stack;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(CompressedGraph graph, boolean saveToMem){
        super(graph, saveToMem);
        traversalType = TraversalType.BFS;
    }


    // BFS traversal
    public void traversal(int start) {
        if(graph.getNumVertex() > 5000) System.out.println("start on: " + start);

        ArrayQueue<Stack<Integer>> queue = new ArrayQueue<>(graph.getNumVertex());


        Stack<Integer> path = new Stack<>();
        path.add(start);

        queue.offer(path);
        if(graph.rowIndex[start + 1] - graph.rowIndex[start] == 0) {
            validPaths.add(getPath(path));
            pathNum ++;
            return;
        }
        while (!queue.isEmpty()) {
            Stack<Integer> currentPath = queue.poll();
            int cur = currentPath.peek();
            for (int i = graph.rowIndex[cur]; i < graph.rowIndex[cur + 1]; i++) {
                int neighbour = graph.colIndex[i];

                Stack<Integer> newStack = new Stack<>();

                newStack.addAll(currentPath);
                newStack.push(neighbour);
                if (graph.getNumDegree(neighbour) == 0) {
                    if(saveToMem) validPaths.add(getPath(newStack));
                    pathNum ++;
                } else queue.offer(newStack);
            }
        }
    }
}
