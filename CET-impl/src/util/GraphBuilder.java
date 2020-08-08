package util;

import Components.Graph;
import util.dagGen.DAGSmith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphBuilder {

    GraphGenerator graphGenerator;
    boolean sparse = false;
    boolean random = false;
    String value = "";

    public GraphBuilder() {
        graphGenerator = new GraphGenerator();
    }

    public Graph readConfig(String fileName) {

        File file = new File(fileName);
        String param = "";

        if (!file.exists()) return null;

        try {
            Scanner scanner = new Scanner(file);

            String type = scanner.nextLine();
            if (type.contains("Sparse")) sparse = true;
            if (type.contains("Random")) random = true;

            param = scanner.nextLine();
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (sparse && random) return generateGraph("sparse random", param);
        if (random) return generateGraph("random", param);
        return generateGraph(fileName, fileName);
    }

    /**
     * This method reads in the type of graph generated
     *
     * @param type: random / file path
     * @return graph
     */
    public Graph generateGraph(String type, String value) {
        Graph graph;
        if (type.equalsIgnoreCase("random")) {
            graph = generateRandomGraph(Integer.parseInt(value));
        } else if (type.equalsIgnoreCase("sparse random")) {
            graph = generateRandomSparseGraph(Integer.parseInt(value));
//            System.out.println("type: " + type);
//            for (Vertex vertex : graph.getVertices()) {
//                System.out.println(vertex.getNeighbours());
//            }
        } else {
            graph = generateGraphFile(value);
        }
        return graph;
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
        boolean[][] dag = smith.generateRandomDAGMatrix(num, 12);

        return graphGenerator.buildGraph(dag);

    }

    public Graph generateRandomSparseGraph(int num) {
        DAGSmith smith = new DAGSmith();

        //generating sparse matrix
        ArrayList<int[]> dag = smith.generateRandomDAGSparseMatrix(num, 12);

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
        return fileGraphParser.readGraph(path);
    }


}
