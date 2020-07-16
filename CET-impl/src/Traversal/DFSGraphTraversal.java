package Traversal;

import Components.Graph;
import Components.Path;

import java.sql.Timestamp;
import java.util.List;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.DFS;
    }

    @Override
    public void traversal(int start) {

        boolean[] visited = new boolean[graph.getNumVertex()];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }

        Path path = new Path(start);

        // Call the recursive helper function to print DFS traversal
        DFStraversal(start, visited, path);
    }


    public void DFStraversal(int s, boolean[] visited, Path path) {
        visited[s] = true;

        if (graph.getEndPoints().contains(s)) {
            if (path.isSatisfied()) {
                validPaths.add(new Path(path.getPathNodes()));
            }
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        List<Integer> edges = graph.getEdges(s);
        for (Integer edge : edges) {
            path.addNode(edge);
            identifyPattern(path);
            DFStraversal(edge, visited, path);
            path.removeNode(edge);

        }

    }

}
