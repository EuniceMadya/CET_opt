import Components.CompressedGraph;
import Traversal.*;
import util.AnchorProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class AlgoExecutor {


    private GraphTraversal algo;
    private long average;
    private int numRun;
    private long[] runTimes;
    private boolean savePathInMem;


    public AlgoExecutor(int numRun) {
        this.numRun = numRun;
        average = 0;
        runTimes = new long[numRun];
    }

    /**
     * Selection of algo to initialize
     * 1. Normal BFS
     * 2. Normal DFS
     * 3. Sequential Hybrid
     * 4. M_CET
     * 5. T_CET
     *
     * @param selection of algo
     */
    public void useAlgo(int selection, CompressedGraph graph) {

        switch (selection) {
            case 1:
                algo = new BFSGraphTraversal(graph, savePathInMem);
//                traversalAlgos.add(new BFSGraphTraversal(graph, null));
                break;

            case 2:
                algo = new DFSGraphTraversal(graph, savePathInMem);
//                traversalAlgos.add(new DFSGraphTraversal(graph, null));
                break;

            case 3:
                addSeqHybrid(graph);
                break;

            case 4:
                algo = new M_CETGraphTraversal(graph, savePathInMem);
//                traversalAlgos.add( new M_CETGraphTraversal(graph, null));
                break;

            case 5:
                algo = new T_CETGraphTraversal(graph, savePathInMem);
//                traversalAlgos.add( new T_CETGraphTraversal(graph, null));
                break;

            default:
                System.out.println("Algo unknown");
        }

    }

    void setSavePathInMem(boolean set){ savePathInMem = set; }

    private void addSeqHybrid(CompressedGraph graph) {
        System.out.println(" \n" +
                "- As you selected hybrid type, \n" +
                "- please specify the anchor nodes selection strategy:\n" +
                "-   1. Random" +
                "    2. Largest degree nodes");
        Scanner sc = new Scanner(System.in);
        String selection = sc.nextLine().equalsIgnoreCase("1") ? "random" : "degrees";
        int numAnchor;

        while (true) {
            System.out.println("\n- Please enter the number of anchors in between:");
            numAnchor = Integer.parseInt(sc.nextLine());
            if (numAnchor + graph.getStartPoints().size() <= graph.getNumVertex()) break;
            System.out.println("WARNING: The number of anchor nodes is larger than the number of nodes in graph, try again.\n\n");
        }


        AnchorProcessor anchorProcessor = new AnchorProcessor();
        int[] anchor = anchorProcessor.findAnchors(graph, selection, numAnchor);

        System.out.println("Anchor nodes: " + Arrays.toString(anchor) + "\n" +
                "Anchors in between show as following:\n");

        for(int i = graph.getStartPoints().size(); i < anchor.length; i ++){
            System.out.print(String.format("[%d, %d] ", anchor[i], graph.getNumDegree(anchor[i])));
        }
        System.out.println("\n");


//        traversalAlgos.add(new SeqHybridGraphTraversal(graph, null, anchor));
        algo = new SeqHybridGraphTraversal(graph, savePathInMem, anchor);
    }

    void runAlgo() {
        System.out.println("Algorithm to execute: " + algo.getClass().getName());

        for (int i = 0; i < numRun; i++) {
            algo.execute();
            average += algo.timeElapsed;
            runTimes[i] = algo.timeElapsed;
            System.out.println("run: " + runTimes[i]);
            System.gc();
        }


    }

    public void writeTimeResult(int nodeNum) {
        File file = new File("OutputFiles/result/timeResults/" + "graph-" + nodeNum + "-" + algo.traversalType + "-" + new Date().toString() + ".txt");

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write("Running: " + algo.getClass().getName());
            for (int i = 0; i < runTimes.length; i++) {
                if (algo != null) {
                    fw.write(String.format("Run %d: %d nanoseconds\n", i + 1, runTimes[i]));
                    System.out.println(String.format("Run %d: %d nanoseconds\n", i + 1, runTimes[i]));
                    System.out.println("Number of paths: "+ algo.pathNum);
                }
            }
            fw.write("\n\nAverage execution time in nanoseconds: " + average / numRun + "\n");
            fw.close();
            System.out.println("\n\nAverage execution time in nanoseconds: " + average / numRun + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSavePathInMem(){
        return savePathInMem;
    }

    public void savePathsResult() {
        algo.saveResults();
    }

    public void printPaths(){
        algo.printPaths();
    }


}
