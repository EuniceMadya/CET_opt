package Traversal;

import Components.Graph;
import Components.Path;
import Components.Vertex;

import java.sql.Timestamp;
import java.util.List;

public class DFSGraphTraversal extends GraphTraversal {

    public DFSGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
        this.window = new Timestamp(Long.MAX_VALUE);
    }

    @Override
    public boolean identifyPattern(Path path) {
        // find pattern which:
        // if adding the node will satisfy the constraint, keep the trend path
        //else abandon the path
        Vertex start =  graph.getVertex(path.getPathNodes().get(0));
        Vertex end = graph.getVertex(path.getPathNodes().get(path.getPathNodes().size() - 1));
        Timestamp timeLap = new Timestamp(end.getTime().getTime() - start.getTime().getTime());


        path.setSatisfied(timeLap.getTime()< window.getTime());


        return path.isSatisfied();
    }

    @Override
    public void traversal(int start) {
        System.out.println("DFS");

        boolean[] visited = new boolean[graph.getNumVertex()];

        Path path = new Path(start);

        // Call the recursive helper function to print DFS traversal
        DFStraversal(start, visited, path);

    }


    public void DFStraversal(int s, boolean[] visited, Path path) {
        visited[s] = true;

        if(graph.getEndPoints().contains(s)){
            if(path.isSatisfied()){
                System.out.println(path.getPathNodes());
            }
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        List<Integer> edges = graph.getEdges(s);
        for (Integer edge : edges) {
            if (!visited[edge]){
                path.addNode(edge);

                DFStraversal(edge, visited, path);
                path.removeNode(edge);
            }
        }

    }



}
// reference
// print all paths from one node to another
//https://www.geeksforgeeks.org/find-paths-given-source-destination/
