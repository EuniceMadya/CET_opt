package Traversal;

import Components.Graph;
import Components.Path;
import util.ArrayQueue;

import java.sql.Timestamp;
import java.util.ArrayList;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.BFS;
    }

    // BFS traversal
    @Override
    public void traversal(int start) {

        boolean[] reached = new boolean[graph.getNumVertex()];

        ArrayList<Path>[] paths = new ArrayList[graph.getNumVertex()];

        ArrayQueue<Integer> queue = new ArrayQueue(graph.getNumVertex());

        for (int i = 0; i < reached.length; i++) {
            reached[i] = false;
            paths[i] = new ArrayList<>();
        }

        reached[start] = true;
        queue.offer(start);
        paths[start].add(new Path(start));


        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbour : graph.getEdges(current)) {
                for (Path path : paths[current]) {

                    Path newPath = new Path(path.getPathNodes(), neighbour);
                    paths[neighbour].add(newPath);
                    identifyPattern(newPath);
                    if (graph.getEndPoints().contains(neighbour)) {
                        if (newPath.isSatisfied()) validPaths.add(newPath);
                    }
                }
                if (!reached[neighbour]) {
                    reached[neighbour] = true;
                    queue.offer(neighbour);
                }

            }
        }


    }


}
