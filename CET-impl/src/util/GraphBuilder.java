package util;

import Components.Graph;
import util.dagGen.DAGSmith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphBuilder {

    GraphGenerator graphGenerator;
    public boolean sparse;
    public boolean random;

    public GraphBuilder() {
        graphGenerator = new GraphGenerator();
        sparse = false;
        random = false;
    }

    public Graph readConfig(String fileName) {

        File file = new File(fileName);
        String param = "";

        if (!file.exists()) return null;

        try {
            Scanner scanner = new Scanner(file);

            String type = scanner.nextLine();
            System.out.println("-- type: " + type);
            if (type.contains("Sparse")) sparse = true;

            if (type.contains("Random")) random = true;

            param = scanner.nextLine();
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(
                "-- sparse: " + sparse + "\n" +
                        "-- random: " + random + "\n");

        return generateGraph(fileName, param);
    }

    /**
     * This method reads in the type of graph generated
     *
     * @param type: random / file path
     * @return graph
     */
    public Graph generateGraph(String type, String value) {
        if (sparse && random) {
            return generateRandomSparseGraph(Integer.parseInt(value));
        }
        if (random) {
            return generateRandomGraph(Integer.parseInt(value));
        }
        return generateGraphFile(type);
    }

    /**
     * Generate random graph from scratch, using online DAGSmith
     *
     * @param num: number of vertices
     * @return graph
     */
    public Graph generateRandomGraph(int num) {

        DAGSmith smith = new DAGSmith();
        //generating adjacency matrix
        boolean[][] dag = smith.generateRandomDAGMatrix(num, num-10);

        return graphGenerator.buildGraph(dag);

    }

    public Graph generateRandomSparseGraph(int num) {
        DAGSmith smith = new DAGSmith();

        //generating sparse matrix
        ArrayList<int[]> dag = smith.generateRandomDAGSparseMatrix(num, num-10);

        System.out.println("Generating sparse matrix, with edges:");
        for (int[] pair : dag) System.out.println(pair[0] + "," + pair[1]);
        System.out.println("\n");

        return graphGenerator.buildGraph(dag, num);
    }

    /**
     * Calling a parser tool to parse the graph file
     *
     * @param path: file path
     * @return graph generated from file
     */
    public Graph generateGraphFile(String path) {
        FileGraphParser fileGraphParser = new FileGraphParser();
        return fileGraphParser.readGraph(path, sparse);
    }


}
