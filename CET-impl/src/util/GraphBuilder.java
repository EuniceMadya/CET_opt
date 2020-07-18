package util;

import Components.Graph;
import Components.Vertex;
import util.dagGen.DAGSmith;

import java.util.ArrayList;

public class GraphBuilder {

    GraphGenerator graphGenerator;

    public GraphBuilder() {
        graphGenerator = new GraphGenerator();
    }

    /**
     * This method reads in the type of graph generated
     *
     * @param type: random / file path
     * @return graph
     */
    public Graph generateGraph(String type) {
        Graph graph;
        if (type.equalsIgnoreCase("random")) {
            //TODO: the number of vertex should be able to be specified in the future
            graph = generateRandomGraph(6);
        } else if (type.equalsIgnoreCase("sparse random")) {
            graph = generateRandomSparseGraph(10);
            System.out.println("type: " + type);
            for (Vertex vertex : graph.getVertices()) {
                System.out.println(vertex.getNeighbours());
            }
        } else {
            graph = generateGraphFile(type);
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
