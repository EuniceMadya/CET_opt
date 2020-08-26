import Components.Graph;
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
        String input = "";

        Graph graph = null;
        Scanner sc = new Scanner(System.in);

        // Read graph type: either random or a file path
        if (args.length == 1) graph = graphBuilder.readConfig(args[0]);
            // it can be read from system input as well.
        else {
            System.out.println("------------------------------------------------------------\n" +
                    "- Do you want to enter an existing config file path? (y/n) -\n" +
                    "-     Enter \"exit\" if you want to terminate program.       -\n" +
                    "------------------------------------------------------------");
            input = sc.nextLine();
            if (!input.equalsIgnoreCase("y")) {
                System.out.println("Choose graph type: \n" +
                        "  1. Random not Sparse \n" +
                        "  2. Random and Sparse");
                String type = sc.nextLine();
                graphBuilder.random = true;


                if (type.equals("2")) graphBuilder.sparse = true;
                System.out.println("Number of nodes:");
                graph = graphBuilder.generateGraph("random or sparse", sc.nextLine());
            } else if (input.equalsIgnoreCase("exit")) return;
            else {
                while (true) {
                    System.out.println("Please specify file path: ");
                    input = sc.nextLine();

                    if (new File(input).exists()) break;

                    System.out.println("File doesn't exist, try again.");
                }
                graph = graphBuilder.readConfig(input);
            }
        }

        // Create output dir
        if (!new File("OutputFiles/result/timeResults").exists())
            new File("OutputFiles/result/timeResults").mkdirs();

        System.out.println("- Output folders created\n" +
                "      ...\n" +
                "\n\n");


        System.out.print("Please enter number of run you want for the algorithm: \n  ");

        AlgoExecutor executor = new AlgoExecutor(graph, Integer.parseInt(sc.nextLine()));


            while(true) {
                System.out.println("------------------------------------------------\n" +
                        "Please add the algorithm to process the graph:\n" +
                        " -   0. Finish choosing\n" +
                        " -   1. Normal BFS\n" +
                        " -   2. Normal DFS\n" +
                        " -   3. Sequential Hybrid\n" +
                        " -   4. M_CET\n" +
                        " -   5. T_CET\n---  ");
                input = sc.nextLine();
                
                if (input.equals("")) continue;
                if(input.equals("0")) return;
                executor.useAlgo(Integer.parseInt(input));
                break;
            }


        System.out.println("Start executing...");

        executor.runAlgos();
    }


}

