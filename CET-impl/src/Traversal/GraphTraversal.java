package Traversal;

import Components.Graph;
import Components.Path;
import Components.Vertex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public abstract class GraphTraversal implements Runnable {
    Graph graph;
    Timestamp window;
    ArrayList<Path> validPaths;
    TraversalType traversalType;


    public GraphTraversal(Graph graph, Timestamp windowSize) {
        this.graph = graph;
        this.window = windowSize;
        this.validPaths = new ArrayList<>();


        //TODO: change it later to actual window size
//        this.window = new Timestamp(Long.MAX_VALUE);

    }


    public boolean identifyPattern(Path path) {
        if (path == null) {
            return false;
        }
        Vertex start = graph.getVertex(path.getPathNodes().get(0));
        Vertex end = graph.getVertex(path.getPathNodes().get(path.getPathNodes().size() - 1));
//        Timestamp timeLap = new Timestamp(end.getTime().getTime() - start.getTime().getTime());
//
//        path.setSatisfied(timeLap.getTime()< window.getTime());
        path.setSatisfied(true);
//
//        return path.isSatisfied();
        return true;
    }

    public abstract void traversal(int i);

    @Override
    public void run() {

        long startTime = System.nanoTime();
        for (int start : graph.getStartPoints()) {
            traversal(start);
            System.out.println("\n\n");
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        showResults(traversalType.toString());

        File file = new File("OutputFiles/result/timeResults" + traversalType.toString() + ".txt");
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write(this.getClass().getName() + "Execution in nanoseconds  : " + timeElapsed);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.getClass().getName() + "Execution in nanoseconds  : " + timeElapsed);

    }

    public void showResults(String algo) {
        File outputFolder = new File("OutputFiles/");
        outputFolder.mkdirs();

        File outputFile = new File("OutputFiles/" + algo + "-" + new Date().toString() + ".txt");

        try {
            outputFile.createNewFile();
            FileWriter fileWriter = new FileWriter(outputFile);
            for (Path singlePath : validPaths) {
                System.out.println(algo + ": " + singlePath.getPathNodes());
                fileWriter.write(singlePath.getPathNodes() + "\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(algo + ": " + e.toString());
        }
    }


}
