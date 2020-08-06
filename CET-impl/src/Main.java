import Components.Graph;
import Traversal.BFSGraphTraversal;
import Traversal.DFSGraphTraversal;
import Traversal.M_CETGraphTraversal;
import Traversal.T_CETGraphTraversal;
import util.GraphBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        GraphBuilder graphBuilder = new GraphBuilder();


//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current relative path is: " + s);

        Graph graph;
        //Read graph type: either random or a file path
        if(args.length == 1){
            graph = readConfig(args[0]);
        }else
            graph = graphBuilder.generateGraph("file","CET-impl/src/InputFiles/grid/inputMeeting0715.txt");
//        Graph graph = graphBuilder.generateGraph("random");
//        Graph graph = graphBuilder.generateGraph("random");

        BFSGraphTraversal bfs = new BFSGraphTraversal(graph, null);
        DFSGraphTraversal dfs = new DFSGraphTraversal(graph, null);
        T_CETGraphTraversal t_cet = new T_CETGraphTraversal(graph, null);
        M_CETGraphTraversal m_cet = new M_CETGraphTraversal(graph, null);

        Thread threadBFS = new Thread(bfs);
        Thread threadDFS = new Thread(dfs);
        Thread threadT_CET = new Thread(t_cet);
        Thread threadM_CET = new Thread(m_cet);


        threadBFS.start();
        threadDFS.start();
//        threadT_CET.start();
//        threadM_CET.start();
    }

    public static Graph readConfig(String configPath) {
        GraphBuilder graphBuilder = new GraphBuilder();
        File file = new File(configPath);

        if(!file.exists()) return null;

        try {
            Scanner scanner = new Scanner(file);

            String type = scanner.nextLine();
            String param = scanner.nextLine();
            scanner.close();

            return graphBuilder.generateGraph(type, param);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

