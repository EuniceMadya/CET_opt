import Components.Graph;
import Traversal.BFSGraphTraversal;
import Traversal.DFSGraphTraversal;
import Traversal.M_CETGraphTraversal;
import Traversal.T_CETGraphTraversal;
import util.GraphBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        GraphBuilder graphBuilder = new GraphBuilder();
        String input;

        Graph graph = null;

        // Read graph type: either random or a file path
        if (args.length == 1) graph = graphBuilder.readConfig(args[0]);
            // it can be read from system input as well.
        else {
            System.out.println("-------------------------------\n" +
                    "- If you want to specify an input config file, input \"y\", \n" +
                    "- else it will enter manually input config menu\n" +
                    "-------------------------------");

            Scanner sc = new Scanner(System.in);
            input = sc.nextLine();
            graphBuilder.random = true;
            if(!input.equalsIgnoreCase("y")) {
                System.out.println("Choose graph type: \n" +
                        "1. Random not Sparse \n" +
                        "2. Random and Sparse");
                String type = sc.nextLine();

                if(type.equals("2")) graphBuilder.sparse = true;
                System.out.println("Number of nodes:");
                graph = graphBuilder.generateGraph("random or sparse", sc.nextLine());
            }
            else{
                while (true) {

                    if (new File(input).exists()) break;

                    System.out.println("File doesn't exist, try again.");
                }
                graph = graphBuilder.readConfig(input);
            }
        }

        // Create output dir
        if (!new File("OutputFiles/result/timeResults").exists())
            new File("OutputFiles/result/timeResults").mkdirs();


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


}

