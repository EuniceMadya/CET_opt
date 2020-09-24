import Components.CompressedGraph;
import Traversal.*;
import util.AnchorProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

class AlgoExecutor {


    private GraphTraversal algo;
    private long average;
    private int numRun;
    private long[] runTimes;
    private boolean savePathInMem;

    // only when choosing sequential
    private String selection;


    AlgoExecutor(int numRun) {
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
     void useAlgo(int selection, CompressedGraph graph) {

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
                "-   1. Random selection\n" +
                "-   2. Largest degree nodes\n" +
                "-   3. Equally distributed nodes");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        selection = input.equals("1") ? "random" : input.equals("2")? "largest" : "distro";
        int numAnchor;

        while (true) {
            System.out.println("\n- Please enter the number of anchors in between:");
            numAnchor = Integer.parseInt(sc.nextLine());
            if (numAnchor + graph.getStartPoints().size() <= graph.getNumVertex()) break;
            System.out.println("WARNING: The number of anchor nodes is larger than the number of nodes in graph, try again.\n\n");
        }
        algo = new SeqHybridGraphTraversal(graph, savePathInMem,  findAnchor(graph, selection, numAnchor));
    }

    private int[] findAnchor(CompressedGraph graph, String selection, int numAnchor){
        AnchorProcessor anchorProcessor = new AnchorProcessor(graph);
        int[] anchor = anchorProcessor.findAnchors(selection, numAnchor);

        System.out.println("Starting nodes: \n");

        for(int i = 0; i < graph.getStartPoints().size(); i ++){
            System.out.print(String.format("[%d, %d] ",
                    graph.getStartPoints().get(i),
                    graph.getNumDegree(graph.getStartPoints().get(i))));
        }
        System.out.println("\nSelected anchor nodes: ");

        for(int i = graph.getStartPoints().size(); i < anchor.length; i ++){
            System.out.print(String.format("[%d, %d] ", anchor[i], graph.getNumDegree(anchor[i])));
        }
        System.out.println("\n");
        return anchor;

    }


    void runAlgo() {
        System.out.println("Algorithm to execute: " + algo.getClass().getName());

        if(algo.traversalType.equals(TraversalType.SeqHybrid) && algo.getGraph().getNumVertex() > 100){
            System.out.println("Do you want to run range of anchor node num?(y/n)\n");
            if(new Scanner(System.in).nextLine().equals("y")){
                for(int i = 5; i < algo.getGraph().getNumVertex() -
                        algo.getGraph().getStartPoints().size() -
                        algo.getGraph().getEndPoints().size();
                    i += 5){
                    ((SeqHybridGraphTraversal)algo).setAnchorNodes(
                            findAnchor(algo.getGraph(),selection, i));
                    runOneAlgo();
                    System.out.println("-- Anchor nodes " + i + " finished!\n\n\n" +
                            "                                 --------\n");
                }
                return;
            }

        }
        runOneAlgo();



    }

    private void runOneAlgo(){
        average = 0;
        for (int i = 0; i < numRun; i++) {
            algo.execute();

            average += algo.timeElapsed;
            runTimes[i] = algo.timeElapsed;
            System.out.println("run: " + runTimes[i] );
            System.gc();
        }
        System.out.println("\n\nAverage execution time in nanoseconds: " + average / numRun + "\n");
        writeTimeResult(algo.getGraph().getNumVertex());
    }

     void writeTimeResult(int nodeNum) {
        File file = new File("OutputFiles/result/timeResults/" + "graph-" + nodeNum + "-" + algo.traversalType + "-" + new Date().toString() + ".txt");

        try {
            if(! file.createNewFile() ) return;
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     boolean isSavePathInMem(){
        return savePathInMem;
    }

     void savePathsResult() {
        algo.saveResults();
    }

     void printPaths(){
        algo.printPaths();
    }


}
