package Traversal;

import Components.Graph;
import Components.Path;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
    }

    @Override
    public void traversal(int start) {

        boolean[] visited = new boolean[graph.getNumVertex()];

        Path path = new Path(start);

        ArrayList<Path> validPaths = new ArrayList<>();

        // Call the recursive helper function to print DFS traversal
        DFStraversal(start, visited, path, validPaths);

        for(Path singlePath : validPaths){
            System.out.println("DFS: "+ singlePath.getPathNodes());
        }

    }


    public void DFStraversal(int s, boolean[] visited, Path path, ArrayList<Path>validPaths) {
        visited[s] = true;

        if(graph.getEndPoints().contains(s)){
            if(path.isSatisfied()){
                validPaths.add(new Path(path.getPathNodes()));
            }
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        List<Integer> edges = graph.getEdges(s);
        for (Integer edge : edges) {
            if (!visited[edge]){
                path.addNode(edge);
                identifyPattern(path);
                DFStraversal(edge, visited, path, validPaths);
                path.removeNode(edge);
            }
        }

    }

}
