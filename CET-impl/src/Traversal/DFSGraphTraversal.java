package Traversal;

import Components.Graph;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.DFS;
    }

    @Override
    public void traversal(int start) {

        boolean[] visited = new boolean[graph.getNumVertex()];
        Arrays.fill(visited, false);

        Stack<Integer> path = new Stack();
        path.push(start);

        if (graph.getVertex(start).getNeighbours().size() != 0) DFStraversal(start, visited, path);

        else validPaths.add(new ArrayList<>(path));
        // Call the recursive helper function to print DFS traversal

    }


    public void DFStraversal(int s, boolean[] visited, Stack path) {
        visited[s] = true;

        if (graph.getEndPoints().contains(s)) {
            if (true) {
                validPaths.add(new ArrayList<>(path));
            }
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        List<Integer> edges = graph.getEdges(s);
        for (Integer edge : edges) {
            path.push(edge);
            DFStraversal(edge, visited, path);
            path.pop();
        }
    }


}
