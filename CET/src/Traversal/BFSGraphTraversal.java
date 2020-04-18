package Traversal;

import java.sql.Time;
import java.util.List;
import java.util.PriorityQueue;

public class BFSGraphTraversal extends GraphTraversal {

    public BFSGraphTraversal(Graph graph, Time windowSize) {
        super(graph, windowSize);
    }

    @Override
    public List<Integer> findPattern() {
        // find pattern which:
        // if adding the node will satisfy the constraint, keep the trend path
        //else abandon the path



        return null;
    }

    // BFS traversal
    @Override
    public void traversal(int start) {
        System.out.println("BFS");
        boolean[] visited = new boolean[graph.numVertex];
        boolean[] reached = new boolean[graph.numVertex];
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
            System.out.print(current + "(" + graph.getVertex(current).time + ")\n");
            for (int neighbour : graph.getEdges(current)) {
                if (reached[neighbour] && !visited[neighbour]) queue.add(neighbour);

            }

        }
    }


}
