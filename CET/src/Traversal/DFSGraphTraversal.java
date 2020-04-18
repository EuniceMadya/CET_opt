package Traversal;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(Graph graph, Time windowSize) {
        super(graph, windowSize);
    }

    @Override
    public List<Integer> findPattern() {
        // find pattern which:
        // if adding the node will satisfy the constraint, keep the trend path
        //else abandon the path


        return null;
    }

    @Override
    public void traversal(int start) {
        System.out.println("DFS");

        boolean[] visited = new boolean[graph.getNumVertex()];

        List<Integer> path = new ArrayList<>();

        // Call the recursive helper function to print DFS traversal
        DFStraversal(start, visited);

    }


    public void DFStraversal(int s, boolean[] visited) {
        visited[s] = true;
        System.out.print(s + "(" + graph.getVertex(s).time + ")\n");

        // Recur for all the vertices adjacent to this vertex
        List<Integer> edges = graph.getEdges(s);
        for (Integer edge : edges) {
            if (!visited[edge])
                DFStraversal(edge, visited);
        }

    }


}
