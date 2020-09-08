package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;
import util.ArrayStack;

import java.sql.Timestamp;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(CompressedGraph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.BFS;
    }

    // BFS traversal
    public void traversal(int start) {

        ArrayQueue<ArrayStack> queue = new ArrayQueue();

        ArrayStack path = new ArrayStack(graph.getNumVertex()/2);
        path.push(start);
        queue.offer(path);
        while (!queue.isEmpty()) {
            ArrayStack currentPath = queue.poll();
            int cur = currentPath.peek();
            for(int i = graph.rowIndex[cur]; i < graph.rowIndex[cur + 1]; i ++){
                int neighbour = graph.colIndex[i];

                ArrayStack newStack = new ArrayStack();
                newStack.addAll(currentPath);
                newStack.push(neighbour);
                if (graph.getNumDegree(neighbour) == 0) {
                    validPaths.add(getPath(newStack.getStack()));
                } else queue.offer(newStack);
            }
        }
    }
}
