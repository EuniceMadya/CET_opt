import Components.Graph;
import util.GraphBuilder;
import util.GraphType;

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
        int numNodes;
        boolean saveResult = false;

        Graph graph;
        Scanner sc = new Scanner(System.in);

        // Read graph type: either random or a file path
        if (args.length == 1) graph = graphBuilder.generateGraphFile(args[0]);
            // it can be read from system input as well.
        else {
            System.out.println("------------------------------------------------------------\n" +
                    "- Do you want to enter an existing config file path? (y/n) -\n" +
                    "-     Enter \"exit\" if you want to terminate program.       -\n" +
                    "------------------------------------------------------------");
            input = sc.nextLine();
            if (!input.equalsIgnoreCase("y")) {
                graphBuilder.random = true;

                System.out.println("- Number of nodes for the graph:");
                numNodes = Integer.parseInt(sc.nextLine());

                System.out.println("\n- Do you want to save the graph to file?(y/n)");
                graphBuilder.saveFile = sc.nextLine().equals("y");

                System.out.println("Choose graph output type: \n" +
                        "  1. Grid \n" +
                        "  2. Pairs\n" +
                        "  3. Lists\n" +
                        "NOTE: Other selection will go to default -- Grid format");

                input = sc.nextLine();

                switch (input) {
                    case "2":
                        graphBuilder.type = GraphType.Pair;
                        break;
                    case "3":
                        graphBuilder.type = GraphType.List;
                        break;
                    default:
                        graphBuilder.type = GraphType.Grid;
                }

                graph = graphBuilder.generateRandomGraph(numNodes);

            } else if (input.equalsIgnoreCase("exit")) return;
            else {
                while (true) {
                    System.out.println("Please specify file path: ");
                    input = sc.nextLine();

                    if (input.equalsIgnoreCase("exit")) return;
                    if (new File(input).exists()) break;

                    System.out.println("File doesn't exist, try again, or type \"exit\" to exit the program");
                }
                graph = graphBuilder.generateGraphFile(input);
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


        while (true) {
            System.out.println("------------------------------------------------\n" +
                    "Please add the algorithm to process the graph:\n" +
                    " -   0. Finish choosing (exit program)\n" +
                    " -   1. Normal BFS\n" +
                    " -   2. Normal DFS\n" +
                    " -   3. Sequential Hybrid\n" +
                    " -   4. M_CET\n" +
                    " -   5. T_CET\n");
            input = sc.nextLine();

            if (input.equals("")) continue;
            if (input.equals("0")) return;
            executor.useAlgo(Integer.parseInt(input));
            break;
        }


        System.out.println("Start executing...");

        executor.runAlgos();
    }


}

