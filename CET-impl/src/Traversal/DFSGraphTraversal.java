package Traversal;

import Components.CompressedGraph;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(CompressedGraph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.DFS;
    }

    @Override
    public void traversal(int start) {

        boolean[] visited = new boolean[graph.getNumVertex()];
        Arrays.fill(visited, false);


        Stack<Integer> path = new Stack();

        path.push(start);

        if (graph.getNumDegree(start) != 0) DFStraversal(start, visited, path);

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
        for (int i = graph.rowIndex[s]; i < graph.rowIndex[s + 1]; i++) {
            int edge = graph.colIndex[i];
            path.push(edge);
            DFStraversal(edge, visited, path);
            path.pop();
        }
    }


}
