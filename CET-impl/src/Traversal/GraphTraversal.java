package Traversal;

import Components.CompressedGraph;

import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.*;

public abstract class GraphTraversal {
    CompressedGraph graph;
    Timestamp window;
    ArrayList<int[]> validPaths;
    public TraversalType traversalType;
    public long timeElapsed;
    public int pathNum;


    public GraphTraversal(CompressedGraph graph, Timestamp windowSize) {
        this.graph = graph;
        this.window = windowSize;
        this.validPaths = new ArrayList<>();
        timeElapsed = 0;
        pathNum = 0;

        //TODO: change it later to actual window size
//        this.window = new Timestamp(Long.MAX_VALUE);

    }

    //TODO: identify patterns of a path
    public boolean identifyPattern(ArrayList<Integer> path) {
        return path != null;

    }

    public abstract void traversal(int i);

    public void execute() {
        if (validPaths.size() > 0) validPaths.clear();

        long startTime = System.nanoTime();
        for (int start : graph.getStartPoints()) {
            traversal(start);
        }
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;


    }

    public void showResults() {
        showResults(traversalType.toString());
    }

    public void showResults(String algo) {
        System.out.println("Write to file...");
        System.out.println(validPaths.size() + "paths to be written.");

        File outputFolder = new File("OutputFiles/");
        outputFolder.mkdirs();

        File outputFile = new File("OutputFiles/" + algo + "-" + graph.getNumVertex() + "V-" + new Date().toString() + ".txt");

        try {
            outputFile.createNewFile();
            FileWriter fileWriter = new FileWriter(outputFile);
            for (int[] singlePath : validPaths) {
                System.out.println(algo + ": " + Arrays.toString(singlePath));
                fileWriter.write(Arrays.toString(singlePath) + "\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(algo + " has error: " + e.toString());
        }
    }

    public int[] getPath(Stack stack){
        Enumeration enumeration = stack.elements();
        int [] path = new int [stack.size()];
        int counter = 0;
        while(enumeration.hasMoreElements()){
            path[counter ++] = (int)enumeration.nextElement();
        }
        return path;
    }

    public int[] getPath(ArrayList<Integer> pathList){
        int [] path = new int [pathList.size()];

        for(int i = 0; i < pathList.size(); i ++){
            path[i] = pathList.get(i);
        }
        return path;
    }




}
