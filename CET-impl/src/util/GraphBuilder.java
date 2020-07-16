package util;

import Components.Graph;
import util.dagGen.DAGSmith;

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
        boolean[][] dag = smith.generateRandomDAG(num, 12);

        return graphGenerator.buildGraph(dag, null);

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
