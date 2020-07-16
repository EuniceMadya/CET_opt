import Components.Graph;
import Traversal.BFSGraphTraversal;
import Traversal.DFSGraphTraversal;
import util.GraphBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {


        GraphBuilder graphBuilder = new GraphBuilder();


        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        //Read graph type: either random or a file path
        Graph graph = graphBuilder.generateGraph("CET-impl/src/InputFiles/inputMeeting0715.txt");

        BFSGraphTraversal bfs = new BFSGraphTraversal(graph, null);
        DFSGraphTraversal dfs = new DFSGraphTraversal(graph, null);

        Thread threadBFS = new Thread(bfs);
        Thread threadDFS = new Thread(dfs);

        threadBFS.start();
        threadDFS.start();


    }


}

