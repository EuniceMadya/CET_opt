package Traversal;

import Components.Graph;
import Components.Path;
import util.ArrayQueue;

import java.sql.Timestamp;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.BFS;
    }

    // BFS traversal
    @Override
    public void traversal(int start) {

        ArrayQueue<Path> queue = new ArrayQueue();

        queue.offer(new Path(start));

        while (!queue.isEmpty()) {
            Path currentPath = queue.poll();
            for(int neighbour : graph.getEdges(currentPath.getEnd())){
                Path newPath = new Path(currentPath.getPathNodes(), neighbour);
                if(graph.getVertex(neighbour).getNeighbours().size() == 0) {
                    identifyPattern(newPath);
                    validPaths.add(newPath);
                }
                else queue.offer(newPath);
            }
        }
    }
}
