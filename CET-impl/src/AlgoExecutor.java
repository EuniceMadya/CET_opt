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
    private String selection = "";
    private int numAnchor = 0;


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
                break;

            case 2:
                algo = new DFSGraphTraversal(graph, savePathInMem);
                break;

            case 3:
                algo = new HybridDFSDFSTraversal(graph, savePathInMem, null);
                addSeqHybrid(graph);
                break;

            case 4:
                algo = new HybridDFSBFSTraversal(graph, savePathInMem, null);
                addSeqHybrid(graph);
                break;

            case 5:
                algo = new M_CETGraphTraversal(graph, savePathInMem);
                break;

            case 6:
                algo = new T_CETGraphTraversal(graph, savePathInMem);
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

        while (true) {
            System.out.println("\n- Please enter the number of anchors in between:");
            numAnchor = Integer.parseInt(sc.nextLine());
            if (numAnchor + graph.getStartPointNum() <= graph.getNumVertex()) break;
            System.out.println("WARNING: The number of anchor nodes is larger than the number of nodes in graph, try again.\n\n");
        }

        ((SeqHybridGraphTraversal)algo).setAnchorNodes(
                findAnchor(algo.getGraph(),selection));
    }

    private int[] findAnchor(CompressedGraph graph, String selection){
        AnchorProcessor anchorProcessor = new AnchorProcessor(graph);
        int[] anchor = anchorProcessor.findAnchors(selection, numAnchor);

        System.out.println("Starting nodes: \n");

        for(int i = 0; i < graph.getStartPointNum(); i ++){
            System.out.print(String.format("[%d, %d] ",
                    graph.getStartPoints().get(i),
                    graph.getNumDegree(graph.getStartPoints().get(i))));
        }
        System.out.println("\nSelected anchor nodes: ");

        for(int i = graph.getStartPointNum(); i < anchor.length; i ++){
            System.out.print(String.format("[%d, %d] ", anchor[i], graph.getNumDegree(anchor[i])));
        }
        System.out.println("\n");
        return anchor;

    }

    void runAlgo() {
        System.out.println("Algorithm to execute: " + algo.getClass().getName());
        String fileName = "OutputFiles/result/timeResults/" + "graph-" +
                algo.getGraph().getNumVertex() + "-" +
                algo.traversalType + "-" +
                new Date().toString() + selection + ".txt";

        if(algo.traversalType.equals(TraversalType.SeqHybrid) && algo.getGraph().getNumVertex() > 100){
            System.out.println("Do you want to run range of anchor node num?(y/n)");
            int upper;

            if(new Scanner(System.in).nextLine().equals("y")){
                while(true){
                    System.out.println("\nDesired upper bound:");
                    try{
                        upper = Integer.parseInt(new Scanner(System.in).nextLine());
                        break;
                    }catch (Exception e){
                        System.out.println("Not a number!");
                    }
                }
                // get the closest int of numAnchor as start point
                numAnchor = numAnchor/5 * 5;
                if(upper < numAnchor) upper = algo.getGraph().getNumVertex()/10 + 10;

                for(int i = numAnchor; i < upper;i += 5){
                    // set new Anchor num
                    numAnchor = i;
                    ((SeqHybridGraphTraversal)algo).setAnchorNodes(
                            findAnchor(algo.getGraph(),selection));
                    runOneAlgo();
                    writeTimeResult(fileName);

                    System.out.println("\n-- Anchor nodes " + i + " finished!\n\n" +
                            "----------------------------------------------------------------------------------------\n\n\n\n");
                }
                return;
            }

        }
        runOneAlgo();
        writeTimeResult(fileName);
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
        System.out.println("\n\nAverage execution time in nanoseconds: " + average / numRun);
        System.out.println("Average execution time in seconds: " + average / numRun / Math.pow(10, 9) + "\n");

    }

     private void writeTimeResult(String fileName) {
        File file = new File(fileName);

        try {
            if(! file.exists()) if(!file.createNewFile() ) return;

            FileWriter fw = new FileWriter(file, true);
//            fw.write("Run: " + algo.getClass().getName());
//            if(numAnchor != 0) fw.write("Anchor num: " + numAnchor + "\n");
//            for (int i = 0; i < runTimes.length; i++) {
//                if (algo != null) {
//                    fw.write(String.format("Run %d: %d nanoseconds\n", i + 1, runTimes[i]));
////                    System.out.println(String.format("Run %d: %d nanoseconds\n", i + 1, runTimes[i]));
//                    System.out.println("Number of paths: "+ algo.pathNum);
//                }
//            }
//            fw.write("\n\nAverage execution time in nanoseconds: " + average / numRun + "\n\n\n\n");
            if(numAnchor != 0){
//                fw.write("selection: "+ selection);
                fw.write("\n" + numAnchor + "," + average/numRun/Math.pow(10, 9) );
            }

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
