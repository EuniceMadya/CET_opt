import Components.CompressedGraph;
import util.GraphBuilder;
import util.GraphType;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Right now it is time: " + new Date().toString());


        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        GraphBuilder graphBuilder = new GraphBuilder();
        String input;
        int numNodes;

        CompressedGraph graph;
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
                while (true) {
                    try {
                        numNodes = Integer.parseInt(sc.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Not a number!");
                        continue;
                    }
                }

                System.out.println("Choose graph output type: \n" +
                        "  1. Grid \n" +
                        "  2. Pairs\n" +
                        "  3. Lists\n" +
                        "  4. CSR(Compressed sparse rwo)\n" +
                        "NOTE: Other selection will go to default -- Grid format");

                input = sc.nextLine();

                System.out.println("\n- Do you want to save the graph to file?(y/n)");
                graphBuilder.saveFile = sc.nextLine().equals("y");

                switch (input) {
                    case "2":
                        graphBuilder.type = GraphType.Pair;
                        break;
                    case "3":
                        graphBuilder.type = GraphType.List;
                        break;
                    case "4":
                        graphBuilder.type = GraphType.CSR;
                        break;
                    default:
                        graphBuilder.type = GraphType.Grid;
                }

                System.out.println("Desired frequency: (requires a float range from 1 to 2)");
                while (true) {
                    try {
                        graphBuilder.frequency = Double.parseDouble(sc.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Not a number, try again!");
                    }
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

        System.out.println("\n\nGraph generated!\n\n");

        System.out.println("Graph has total of " + graph.getTotalNumEdges() + " edges");

        printDegreeNumVSNode(graph);

        // Create output dir
        if (!new File("OutputFiles/result/timeResults").exists())
            new File("OutputFiles/result/timeResults").mkdirs();

        System.out.println("- Output folders created\n" +
                "      ...\n" +
                "\n\n");

        System.out.println(Arrays.toString(graph.getColIndex()));
        System.out.println(Arrays.toString(graph.getRowIndex()));


        System.out.print("Please enter number of run you want for the algorithm: \n");

        System.gc();
        AlgoExecutor executor;

        while (true) {
            try {
                executor = new AlgoExecutor(Integer.parseInt(sc.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Not a number!");
            }
        }


        while (true) {
            System.out.println("------------------------------------------------\n" +
                    "Please add the algorithm to process the graph:\n" +
                    " -   0. Finish choosing (exit program)\n" +
                    " -   1. Normal BFS\n" +
                    " -   2. Normal DFS\n" +
                    " -   3. Anchor (DFS concatenate)\n" +
                    " -   4. Anchor (BFS concatenate)\n" +
                    " -   5. M_CET\n" +
                    " -   6. T_CET\n" +
                    " -   7. Anchor (Double leveling)\n");

            input = sc.nextLine();

            if (input.equals("")) continue;

            if (input.equals("0")) return;

            System.out.println("\n\n- Do you want to save result to run time memory? (y/n)");
            executor.setSavePathInMem(sc.nextLine().equalsIgnoreCase("y"));

            executor.useAlgo(Integer.parseInt(input), graph);
            break;
        }


        System.out.println("Start executing...");

        executor.runAlgo();

        executor.cleanGarbage();

        System.out.println("\n\n- Run finished");

        if (executor.isSavePathInMem()) {
            System.out.println("\n\n- Paths are now stored in memory. \n" +
                    "Do you want to save result to files? (y/n)");

            if (sc.nextLine().equals("y")) {

                System.out.println("Writing results...\n");
                executor.savePathsResult();
            }

            System.out.println("\n\n- Do you want to print out results? (y/n)");
            if (sc.nextLine().equals("y")) {
                executor.printPaths();
            }
        } else
            System.out.println("Warning: No results saved.\n");


    }


    private static void printDegreeNumVSNode(CompressedGraph graph){
        TreeMap<Integer, Integer> degreeNum = new TreeMap<>(Collections.reverseOrder());

        for(int i = 0; i < graph.getNumVertex(); i ++){
            int degree = graph.getNumDegree(i);
            if(degreeNum.get(degree) == null) degreeNum.put(degree, 1);
            else degreeNum.replace(degree, degreeNum.get(degree) + 1);
        }
        printDegrees(degreeNum);


        System.out.println("\n\n\nnot source or sink: ");
        TreeMap<Integer, Integer> interDegreeNum = new TreeMap<>(Collections.reverseOrder());

        for(int i = 0; i < graph.getNumVertex(); i ++){
            int degree = graph.getNumDegree(i);
            if(!graph.startContains(i) && !graph.endContains(i))
                if(interDegreeNum.get(degree) == null) interDegreeNum.put(degree, 1);
                else interDegreeNum.replace(degree, interDegreeNum.get(degree) + 1);
        }
        printDegrees(interDegreeNum);
        System.out.println("-------------------------------------------------------------");

        System.out.println("\n\n\nIncoming degree nodes: ");
        degreeNum.clear();

        for(int i = 0; i < graph.getNumVertex(); i ++){
            int degree = graph.getIndegree(i);
            if(degreeNum.get(degree) == null) degreeNum.put(degree, 1);
            else degreeNum.replace(degree, degreeNum.get(degree) + 1);
        }
        printDegrees(degreeNum);

        System.out.println("\n\n\nIncoming degree nodes: ");
        interDegreeNum.clear();

        for(int i = 0; i < graph.getNumVertex(); i ++){
            int degree = graph.getIndegree(i);
            if(!graph.startContains(i) && !graph.endContains(i))
            if(interDegreeNum.get(degree) == null) interDegreeNum.put(degree, 1);
            else interDegreeNum.replace(degree, interDegreeNum.get(degree) + 1);
        }
        printDegrees(degreeNum);

    }

    private static void printDegrees(TreeMap<Integer, Integer> interDegreeNum) {
        Set set;
        Iterator iterator;
        set = interDegreeNum.entrySet();
        iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
    }


}

