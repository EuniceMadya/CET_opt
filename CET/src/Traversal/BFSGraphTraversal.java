package Traversal;

import Components.Graph;
import Components.Path;

import java.sql.Timestamp;
import java.util.PriorityQueue;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
    }

    @Override
    public boolean identifyPattern(Path path) {
        // find pattern which:
        // if adding the node will satisfy the constraint, keep the trend path
        //else abandon the path



        return true;
    }

    // BFS traversal
    @Override
    public void traversal(int start) {
        System.out.println("BFS");
        boolean[] visited = new boolean[graph.getNumVertex()];
        boolean[] reached = new boolean[graph.getNumVertex()];
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
            reached[i] = false;
        }

        visited[start] = true;
        reached[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            visited[current] = true;
//            System.out.print(current + "(" + graph.getVertex(current).getTime() + ")\n");
            for (int neighbour : graph.getEdges(current)) {
                if (reached[neighbour] && !visited[neighbour]) queue.add(neighbour);

            }

        }
    }


}
