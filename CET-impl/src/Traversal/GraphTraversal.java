package Traversal;

import Components.Graph;
import Components.Vertex;

import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public abstract class GraphTraversal {
    Graph graph;
    Timestamp window;
    ArrayList<ArrayList<Integer>> validPaths;
    public TraversalType traversalType;
    public long timeElapsed;


    public GraphTraversal(Graph graph, Timestamp windowSize) {
        this.graph = graph;
        this.window = windowSize;
        this.validPaths = new ArrayList<>();
        timeElapsed = 0;


        //TODO: change it later to actual window size
//        this.window = new Timestamp(Long.MAX_VALUE);

    }


    public boolean identifyPattern(ArrayList<Integer> path) {
        if (path == null) {
            return false;
        }
        Vertex start = graph.getVertex(path.get(0));
        Vertex end = graph.getVertex(path.get(path.size()-1));
//        Timestamp timeLap = new Timestamp(end.getTime().getTime() - start.getTime().getTime());
//
//        path.setSatisfied(timeLap.getTime()< window.getTime());//
//        return path.isSatisfied();
        return true;
    }

    public abstract void traversal(int i);

    public void execute() {

        long startTime = System.nanoTime();
        for (int start : graph.getStartPoints()) {
            traversal(start);
            System.out.println("\n\n");
        }
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        showResults(traversalType.toString());
    }

    public void showResults(String algo) {
        File outputFolder = new File("OutputFiles/");
        outputFolder.mkdirs();

        File outputFile = new File("OutputFiles/" + algo + "-" + new Date().toString() + ".txt");

        try {
            outputFile.createNewFile();
            FileWriter fileWriter = new FileWriter(outputFile);
            for (ArrayList<Integer> singlePath : validPaths) {
                System.out.println(algo + ": " + singlePath);
                fileWriter.write(singlePath + "\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(algo + ": " + e.toString());
        }
    }


}
