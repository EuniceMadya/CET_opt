import Components.CompressedGraph;

import Traversal.*;
import util.AnchorProcessor;
import util.AnchorType;

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
    private AnchorType selection = null;
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
                addHybrid(graph, ConcatenateType.DFS);
                break;

            case 4:
                addHybrid(graph, ConcatenateType.BFS);
                break;

            case 5:
                algo = new M_CETGraphTraversal(graph, savePathInMem);
                break;

            case 6:
                algo = new T_CETGraphTraversal(graph, savePathInMem);
                break;

            case 7:
                addDoubleSeqHybrid(graph);
                break;

            default:
                System.out.println("Algo unknown");
        }

    }

    void setSavePathInMem(boolean set) {
        savePathInMem = set;
    }

    private void addDoubleSeqHybrid(CompressedGraph graph){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n" +
                "First level concatenation: 1. BFS   2. DFS");
        ConcatenateType firstConcatenate = sc.nextLine().equals("1") ? ConcatenateType.BFS : ConcatenateType.DFS;

        System.out.println("\n" +
                "Second level concatenation: 1. BFS   2. DFS");
        ConcatenateType secondConcatenate = sc.nextLine().equals("1") ? ConcatenateType.BFS : ConcatenateType.DFS;

        System.out.println("\n" +
                "Reduce Anchor type: 1. Half   2. Keep largest degree");
        String reduceType = sc.nextLine().equals("1") ? "Half" : "largest";

        System.out.println("\n" +
                "Do you want to run it concurrently?(y/n)");
        String input = sc.nextLine();
        if(input.equalsIgnoreCase("y"))
            algo = new ConcurrentDoubleAnchorTraversal(graph, savePathInMem, null, firstConcatenate, secondConcatenate, reduceType);
        else
            algo = new DoubleAnchorTraversal(graph, savePathInMem, null, firstConcatenate, secondConcatenate, reduceType);

        selectAnchorType(graph);

    }

    private void addHybrid(CompressedGraph graph, ConcatenateType concatenateType) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n" +
                "Do you want to run it concurrently?(y/n)");
        String input = sc.nextLine();
        if(input.equals("y")) algo = new ConcurrentAnchorTraversal(graph, savePathInMem, null, concatenateType);
        else algo = new AnchorGraphTraversal(graph, savePathInMem, null,concatenateType );

        selectAnchorType(graph);
    }


    // Add anchor nodes to algorithm
    private void selectAnchorType(CompressedGraph graph){
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n-----\n" +
                "- As you selected hybrid type, \n" +
                "- please specify the anchor nodes selection strategy:\n" +
                "-   1. Random selection\n" +
                "-   2. Largest degree nodes\n" +
                "-   3. Equally distributed nodes");
        String input = sc.nextLine();
        selection = input.equals("1") ? AnchorType.RANDOM : input.equals("2") ? AnchorType.LARGEST_DEGREE : AnchorType.EQUAL_DISTRIBUTE;

        while (true) {
            System.out.println("\n- Please enter the number of anchors in between:");
            numAnchor = Integer.parseInt(sc.nextLine());
            if (numAnchor + graph.getStartPointNum() <= graph.getNumVertex()) break;
            System.out.println("WARNING: The number of anchor nodes is larger than the number of nodes in graph, try again.\n\n");
        }

        ((AnchorGraphTraversal) algo).setAnchorNodes(
                findAnchor(algo.getGraph(), selection));
    }

    private int[] findAnchor(CompressedGraph graph, AnchorType selection) {
        AnchorProcessor anchorProcessor = new AnchorProcessor(graph);
        int[] anchor = anchorProcessor.findAnchors(selection, numAnchor);

        System.out.println("\nSource nodes:");

        for (int i = 0; i < graph.getStartPointNum(); i++) {
            System.out.print(String.format("[%d, %d] ",
                    graph.getStartPoints().get(i),
                    graph.getNumDegree(graph.getStartPoints().get(i))));
        }
        System.out.println("\nSelected anchor nodes: ");

        for (int i = graph.getStartPointNum(); i < anchor.length; i++) {
            System.out.print(String.format("[%d, %d] ", anchor[i], graph.getNumDegree(anchor[i])));
        }
        System.out.println("\n");
        return anchor;

    }

    void runAlgo() {

        System.out.println("Algorithm to execute: " + algo.getClass().getName());
        boolean isDoubleConcatenate = algo.getClass().getName().contains("Double");

        String concurrentPrefix = (algo.getClass().getName().contains("Concurrent") ? "Concurrent-" : "");

        String doublePrefix = (isDoubleConcatenate ?
            ((DoubleAnchorTraversal)algo).firstLevel +
                "-"+((DoubleAnchorTraversal)algo).secondLevel+ "-":"")
                .replaceAll("AnchorType.", "");

        String concatenatePrefix = "";
        if(selection != null){
            if(isDoubleConcatenate) concatenatePrefix ="-" +  ((DoubleAnchorTraversal)algo).firstLevel + "" +  ((DoubleAnchorTraversal)algo).secondLevel;
            else concatenatePrefix = "-" + ((AnchorGraphTraversal)algo).concatenateType;
            concatenatePrefix.replace("ConcatenateType.", "");
        }


        String fileName = "OutputFiles/result/timeResults/" + "graph-" +
                algo.getGraph().getNumVertex() + "-" +
                algo.traversalType +
               doublePrefix   + concurrentPrefix +
                selection +concatenatePrefix +  "-" + new Date().toString() +  ".txt";

        if (selection!= null && algo.getGraph().getNumVertex() > 100) {
            System.out.println("Do you want to run range of anchor node num?(y/n)");
            int upper;

            if (new Scanner(System.in).nextLine().equals("y")) {
                while (true) {
                    System.out.println("\nDesired upper bound:");
                    try {
                        upper = Integer.parseInt(new Scanner(System.in).nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Not a number!");
                    }
                }
                // get the closest int of numAnchor as start point
                numAnchor = numAnchor / 5 * 5;
                if (upper < numAnchor) upper = algo.getGraph().getNumVertex() / 10 + 10;

                for (int i = numAnchor; i <= upper; i += 5) {
                    // set new Anchor num
                    numAnchor = i;
                    ((AnchorGraphTraversal) algo).setAnchorNodes(
                            findAnchor(algo.getGraph(), selection));
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


    public void cleanGarbage(){
        System.gc();
        if(algo.getClass().getName().contains("ConcurrentDouble"))
            ((ConcurrentDoubleAnchorTraversal)algo).pool.shutdownNow();
        else if(algo.getClass().getName().contains("ConcurrentAnchor"))
            ((ConcurrentAnchorTraversal)algo).pool.shutdownNow();
    }

    private void runOneAlgo() {
        average = 0;
        for (int i = 0; i < numRun; i++) {
            algo.execute();

            average += algo.timeElapsed;
            runTimes[i] = algo.timeElapsed;
            System.out.println("run: " + runTimes[i]);
            System.gc();
        }

        System.out.println("\n\nAverage execution time in nanoseconds: " + average / numRun);
        System.out.println("Average execution time in seconds: " + average / numRun / Math.pow(10, 9) + "\n");

    }

    private void writeTimeResult(String fileName) {
        File file = new File(fileName);

        try {
            if (!file.exists()) if (!file.createNewFile()) return;

            FileWriter fw = new FileWriter(file, true);

            if (numAnchor != 0)
                fw.write("\n" + numAnchor + "," + average / numRun / Math.pow(10, 9));
            else
                fw.write("Average time(s) running " + numRun + " times: " + average / numRun / Math.pow(10, 9));

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isSavePathInMem() {
        return savePathInMem;
    }

    void savePathsResult() {
        algo.saveResults();
    }

    void printPaths() {
        algo.printPaths();
    }


}
