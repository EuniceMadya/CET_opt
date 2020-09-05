package util;

import Components.CompressedGraph;
import Components.Graph;
import util.dagGen.DAGSmith;
import util.dagGen.DAGTools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GraphBuilder {

    GraphGenerator graphGenerator;
    public boolean random;
    public boolean saveFile = false;
    public GraphType type;

    public GraphBuilder() {
        graphGenerator = new GraphGenerator();
        random = false;
    }


    /**
     * This method reads in the type of graph generated
     *
     * @param numNodes: random / file path
     * @return graph
     */
    public Graph generateRandomGraph(int numNodes) {

        System.out.println("Random graph generating...");

        if (type.equals(GraphType.Grid)) return generateRandomMatrixGraph(numNodes);

        if (type.equals(GraphType.Pair)) return generateRandomCompressPairGraph(numNodes);

        if (type.equals(GraphType.List)) return generateRandomCompressListGraph(numNodes);

        if (type.equals(GraphType.CSR)) return generateRandomCSRGraph(numNodes);


        return null;

    }

    /**
     * Generate random graph from scratch, using online DAGSmith
     *
     * @param num: number of vertices
     * @return graph
     */
    private Graph generateRandomMatrixGraph(int num) {

        DAGSmith smith = new DAGSmith();
        //generating adjacency matrix
        boolean[][] dag = smith.generateDAGMatrix(num, num + 20);
        StringBuilder sb = new StringBuilder("Grid\n" + num + "\n");
        sb.append(DAGTools.printDAG(dag));
        System.out.println(sb.toString());
        if (saveFile) {

            saveToFile(sb.toString().trim(), num);
        }
        return graphGenerator.buildGraph(dag);

    }

    private Graph generateRandomCompressPairGraph(int num) {
        DAGSmith smith = new DAGSmith();

        //generating sparse matrix
        ArrayList<int[]> dag = smith.generateDAGPairs(num, num > 50 ? num : 20);
        StringBuilder sb = new StringBuilder("Pair\n" + num + "\n");
        for (int[] pair : dag) {
            sb.append(pair[0] + "," + pair[1] + "\n");
        }
        System.out.println(sb.toString());
        if (saveFile) {

            saveToFile(sb.toString().trim(), num);
        }

        return graphGenerator.buildGraph(dag, num);
    }

    private Graph generateRandomCompressListGraph(int num) {
        DAGSmith smith = new DAGSmith();

        //generating sparse matrix
        ArrayList<Integer>[] dag = smith.generateDAGLists(num, num > 50 ? num : 20);
        StringBuilder sb = new StringBuilder("List\n" + num + "\n");
        for (ArrayList<Integer> list : dag) {
            String s = list.size() == 0 ? "NaN" :
                    list.toString().replace(
                            "[", "").replace("]", "").replace(" ", "");

            sb.append(s);
            sb.append("\n");
        }
        System.out.println(sb.toString());
        if (saveFile) {

            saveToFile(sb.toString().trim(), num);
        }

        return graphGenerator.buildGraph(dag);
    }

    public CompressedGraph generateRandomCSRGraph(int num) {
        DAGSmith smith = new DAGSmith();

        //generating CSR form graph
        CompressedGraph dag = smith.generateDAGCSR(num, num > 50 ? num : 20);
        StringBuilder sb = new StringBuilder("CSR\n" + num + "\ncol:");
        for (int i : dag.getColIndex()) {
            sb.append(" " + i);
        }
        sb.append("\nrow:");
        for (int i : dag.getRowIndex()) {
            sb.append(" " + i);
        }
        System.out.println(sb.toString());
        if (saveFile) {
            saveToFile(sb.toString(), num);
        }
        return dag;

    }


    private void saveToFile(String string, int num) {

        File file = new File(type.toString().replace("GraphType.", "") + num + ".txt");

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write(string);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
