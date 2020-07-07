import Components.Graph;
import Traversal.BFSCustomTraversal;
import Traversal.BFSGraphTraversal;
import Traversal.DFSGraphTraversal;
import util.GraphBuilder;

public class Utility {
    public static void main(String[] args) {
        GraphBuilder graphBuilder = new GraphBuilder();
        Graph graph = graphBuilder.generateGraph("random");
        BFSGraphTraversal bfs = new BFSGraphTraversal(graph, null);
//        bfs.traversal(0);

        BFSCustomTraversal bfsCustom = new BFSCustomTraversal(graph, null);

        DFSGraphTraversal dfs = new DFSGraphTraversal(graph, null);
        //        dfs.traversal(2);


        Thread threadBFS = new Thread(bfs);
        Thread threadDFS = new Thread(dfs);
        Thread threadCustomBFS = new Thread(bfsCustom);

        threadBFS.start();
        threadDFS.start();
        threadCustomBFS.start();


    }


}

