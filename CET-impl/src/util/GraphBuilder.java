package util;

import Components.CompressedGraph;
import util.dagGen.DAGSmith;
import util.dagGen.DAGTools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GraphBuilder {

    public boolean random;
    public boolean saveFile = false;
    public GraphType type;

    private GraphGenerator graphGenerator;
    public double frequency;


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
    public CompressedGraph generateRandomGraph(int numNodes) {

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
    private CompressedGraph generateRandomMatrixGraph(int num) {

        DAGSmith smith = new DAGSmith();
        //generating adjacency matrix
        boolean[][] dag = smith.generateDAGMatrix(num, getFrequency(num));
        StringBuilder sb = new StringBuilder("Grid\n" + num + "\n");
        sb.append(DAGTools.printDAG(dag));
        System.out.println(sb.toString());
        if (saveFile) {

            saveToFile(sb.toString().trim(), num);
        }
        return graphGenerator.buildGraph(dag);

    }

    private CompressedGraph generateRandomCompressPairGraph(int num) {
        DAGSmith smith = new DAGSmith();

        //generating sparse matrix
        ArrayList<int[]> dag = smith.generateDAGPairs(num, getFrequency(num));
        StringBuilder sb = new StringBuilder("Pair\n" + num + "\n");
        for (int[] pair : dag) {
            sb.append(pair[0]).append(",").append(pair[1]).append("\n");
        }
        System.out.println(sb.toString());
        if (saveFile) {

            saveToFile(sb.toString().trim(), num);
        }

        return graphGenerator.buildGraph(dag, num);
    }

    private CompressedGraph generateRandomCompressListGraph(int num) {
        DAGSmith smith = new DAGSmith();

        //generating sparse matrix
        ArrayList<Integer>[] dag = smith.generateDAGLists(num, getFrequency(num));
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

    private CompressedGraph generateRandomCSRGraph(int num) {
        DAGSmith smith = new DAGSmith();

        //generating CSR form graph
        CompressedGraph dag = smith.generateDAGCSR(num, getFrequency(num));
        StringBuilder sb = new StringBuilder("CSR\n" + num + "\ncol:");
        for (int i : dag.getColIndex()) {
            sb.append(" ").append(i);
        }
        sb.append("\nrow:");
        for (int i : dag.getRowIndex()) {
            sb.append(" ").append(i);
        }
        System.out.println(sb.toString());
        if (saveFile) {
            saveToFile(sb.toString(), num);
        }
        return dag;

    }


    private void saveToFile(String string, int num) {
        String fileName = type.toString().replace(
                "GraphType.", "") + num + "-f" + frequency + ".txt";

        File file = new File(fileName);

        try {
            if (!file.exists())
                file.createNewFile();


            FileWriter fw = new FileWriter(file, true);
            fw.write(string.substring(0, string.length() / 2));
            fw.write(string.substring(string.length() / 2));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int getFrequency(int num) {
        return (int) Math.round(num * frequency);
    }

    /**
     * Calling a parser tool to parse the graph file
     *
     * @param path: file path
     * @return graph generated from file
     */
    public CompressedGraph generateGraphFile(String path) {
        FileGraphParser fileGraphParser = new FileGraphParser();
        return fileGraphParser.readGraph(path);
    }


}
