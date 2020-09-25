package Traversal;

import Components.CompressedGraph;

import java.io.File;
import java.io.FileWriter;
import java.sql.Time;
import java.util.*;

public abstract class GraphTraversal {
    CompressedGraph graph;
    ArrayList<int[]> validPaths;
    public TraversalType traversalType;
    public long timeElapsed;
    public long pathNum;
    boolean saveToMem;


     GraphTraversal(CompressedGraph graph, boolean saveToMem) {
        this.graph = graph;
        this.saveToMem = saveToMem;
        this.validPaths = new ArrayList<>();
        timeElapsed = 0;
        pathNum = 0;
    }

    public GraphTraversal(CompressedGraph graph) {
        this(graph, true);
    }

    //TODO: identify patterns of a path
    public boolean identifyPattern(ArrayList<Integer> path) {
        return path != null;

    }

    public CompressedGraph getGraph(){
        return graph;
    }

    public abstract void traversal(int i);

    public void execute() {
        pathNum = 0;
        if (validPaths.size() > 0) validPaths.clear();

        System.out.println("Number of start points: " + graph.getStartPointNum());
        int i = 1;

        long startTime = System.nanoTime();
        for (int start : graph.getStartPoints()) {
            if (graph.getNumVertex() > 5000) {
                System.out.println("Execute: " + (i++));
                System.out.println(new Time(System.currentTimeMillis()).toString() + " - start on: " + start + " with degree " + graph.getNumDegree(start));
            }

            traversal(start);
        }
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;


    }

    public void saveResults() {
        saveResults(traversalType.toString());
    }

    void saveResults(String algo) {
        System.out.println("Write to file...");
        System.out.println(validPaths.size() + " paths to be written.");

        File outputFolder = new File("OutputFiles/");
        outputFolder.mkdirs();

        File outputFile = new File("OutputFiles/" + algo + "-" + graph.getNumVertex() + "V-" + new Date().toString() + ".txt");
        int maxLength = 0;
        try {
            outputFile.createNewFile();
            FileWriter fileWriter = new FileWriter(outputFile);
            for (int[] singlePath : validPaths) {
                if (maxLength < singlePath.length) maxLength = singlePath.length;
                fileWriter.write("(" + singlePath.length + ")");
                fileWriter.write(Arrays.toString(singlePath) + "\n");
            }
            fileWriter.write("Longest path has " + maxLength + " nodes.");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(algo + " has error: " + e.toString());
        }
    }

    int[] getPath(Stack stack) {
        Enumeration enumeration = stack.elements();
        int[] path = new int[stack.size()];
        int counter = 0;
        while (enumeration.hasMoreElements()) {
            path[counter++] = (int) enumeration.nextElement();
        }
        return path;
    }

    public void printPaths() {
        for (int[] singlePath : validPaths) {
            System.out.println(traversalType.toString() + ": " + Arrays.toString(singlePath));
        }
    }

    int[] getPath(ArrayList<Integer> pathList) {
        int[] path = new int[pathList.size()];

        for (int i = 0; i < pathList.size(); i++) {
            path[i] = pathList.get(i);
        }
        return path;
    }


}
