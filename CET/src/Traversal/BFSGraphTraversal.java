package Traversal;

import Components.Graph;
import Components.Path;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
    }

//    @Override
//    public boolean identifyPattern(Path path) {
//        // find pattern which:
//        // if adding the node will satisfy the constraint, keep the trend path
//        //else abandon the path
//
//
//
//        return true;
//    }

    // BFS traversal
    @Override
    public void traversal(int start) {

        boolean[] reached = new boolean[graph.getNumVertex()];

        ArrayList<Path>[] paths = new ArrayList[graph.getNumVertex()];

        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int i = 0; i < reached.length; i++) {
            reached[i] = false;
            paths[i] = new ArrayList<>();
        }

        reached[start] = true;
        queue.add(start);
        paths[start].add(new Path(start));

        ArrayList<Path> finalPaths = new ArrayList<>();

        while (!queue.isEmpty()) {
            int current = queue.poll();

//            System.out.print(current + " ");
            for (int neighbour : graph.getEdges(current)) {
                for (Path path : paths[current]) {

                    Path newPath = new Path(path.getPathNodes(), neighbour);
                    paths[neighbour].add(newPath);
                    identifyPattern(newPath);
                    if (graph.getEndPoints().contains(neighbour)) {
                        if (newPath.isSatisfied()) finalPaths.add(newPath);
                    }
                }
                if (!reached[neighbour]) {
                    reached[neighbour] = true;
                    queue.add(neighbour);
                }

            }
        }

        for (Path path : finalPaths) {
            if (identifyPattern(path)) {
                System.out.println("BFS " + path.getPathNodes());
            }


        }
    }


}
