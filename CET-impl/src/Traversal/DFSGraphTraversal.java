package Traversal;

import Components.CompressedGraph;

import java.util.Stack;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(CompressedGraph graph, boolean saveToMem) {
        super(graph, saveToMem);
        traversalType = TraversalType.DFS;

    }

    @Override
    public void traversal(int start) {
        Stack<Integer> path = new Stack<>();

        path.push(start);

        // Call the recursive helper function to print DFS traversal
        if (graph.getNumDegree(start) != 0) DFStraversal(start, path);

        else {
            if (saveToMem) validPaths.add(getPath(path));
            pathNum++;
        }

    }

    private void DFStraversal(int s, Stack<Integer> path) {

        if (graph.endContains(s)) {
            if (saveToMem) validPaths.add(getPath(path));
            pathNum++;
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        for (int i = graph.rowIndex[s]; i < graph.rowIndex[s + 1]; i++) {
            int edge = graph.colIndex[i];
            path.push(edge);
            DFStraversal(edge, path);
            path.pop();
        }
    }


}
