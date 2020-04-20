import Components.Graph;
import Traversal.BFSGraphTraversal;
import Traversal.DFSGraphTraversal;
import util.GraphBuilder;

public class Utility {
    public static void main(String[] args) {
        GraphBuilder graphBuilder = new GraphBuilder();
        Graph graph = graphBuilder.generateGraph("random");
        BFSGraphTraversal bfs = new BFSGraphTraversal(graph, null);
//        bfs.traversal(0);

        DFSGraphTraversal dfs = new DFSGraphTraversal(graph, null);

        Thread threadBFS = new Thread(bfs);
        Thread threadDFS = new Thread(dfs);
//        dfs.traversal(2);

        threadBFS.start();
        threadDFS.start();


    }


}

