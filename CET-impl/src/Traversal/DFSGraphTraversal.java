package Traversal;

import Components.CompressedGraph;

import java.util.Arrays;
import java.util.Stack;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(CompressedGraph graph, boolean saveToMem){
        super(graph, saveToMem);
        traversalType = TraversalType.DFS;

    }

    @Override
    public void traversal(int start) {

        boolean[] visited = new boolean[graph.getNumVertex()];
        Arrays.fill(visited, false);


        Stack<Integer> path = new Stack<>();

        path.push(start);

        // Call the recursive helper function to print DFS traversal
        if (graph.getNumDegree(start) != 0) DFStraversal(start, visited, path);

        //TODO: remember to un-comment this
        else
            if(saveToMem) validPaths.add(getPath(path));

    }


    private void DFStraversal(int s, boolean[] visited, Stack path) {
        visited[s] = true;

        if (graph.getEndPoints().contains(s)) {
            // TODO: might need to add identify pattern later
            if(saveToMem) validPaths.add(getPath(path));
            pathNum++;

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
