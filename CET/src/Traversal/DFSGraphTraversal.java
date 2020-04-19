package Traversal;

import Components.Graph;
import Components.Path;

import java.sql.Timestamp;
import java.util.List;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
    }

   // public boolean identifyPattern(Path path) {
        // find pattern which:
        // if adding the node will satisfy the constraint, keep the trend path
        //else abandon the path

   // }

    @Override
    public void traversal(int start) {


        boolean[] visited = new boolean[graph.getNumVertex()];

        Path path = new Path(start);

        // Call the recursive helper function to print DFS traversal
        DFStraversal(start, visited, path);

    }


    public void DFStraversal(int s, boolean[] visited, Path path) {
        visited[s] = true;

        if(graph.getEndPoints().contains(s)){
            if(path.isSatisfied()){
                System.out.println("DFS: "+ path.getPathNodes());
            }
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        List<Integer> edges = graph.getEdges(s);
        for (Integer edge : edges) {
            if (!visited[edge]){
                path.addNode(edge);
                identifyPattern(path);
                DFStraversal(edge, visited, path);
                path.removeNode(edge);
            }
        }

    }



}
// reference
// print all paths from one node to another
//https://www.geeksforgeeks.org/find-paths-given-source-destination/
