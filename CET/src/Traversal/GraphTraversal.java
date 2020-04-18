package Traversal;

import Components.Graph;
import Components.Path;

import java.sql.Timestamp;

public abstract class GraphTraversal implements Runnable {
    Graph graph;
    Timestamp window;

    public GraphTraversal(Graph graph, Timestamp windowSize) {
        this.graph = graph;
        this.window = windowSize;
    }


    public abstract boolean identifyPattern(Path path);

    public abstract void traversal(int i);

    @Override
    public void run(){

        long startTime = System.nanoTime();
        for (int start : graph.getStartPoints()) {

            traversal(start);
            System.out.println("\n\n");
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println(this.getClass().getName() + "Execution in nanoseconds  : " + timeElapsed);
    }


}
