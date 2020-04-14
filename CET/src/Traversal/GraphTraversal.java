package Traversal;

import java.sql.Time;
import java.util.List;

public abstract class GraphTraversal implements Runnable {
    Graph graph;
    Time window;

    public GraphTraversal(Graph graph, Time windowSize) {
        this.graph = graph;
        this.window = windowSize;
    }


    public abstract List<Integer> findPattern();

    public abstract void traversal(int i);

    @Override
    public void run(){

        long startTime = System.nanoTime();
        for (int start : graph.getStartPoints()) {

            traversal(start);
            System.out.println("\n");
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println(this.getClass().getName() + "Execution in nanoseconds  : " + timeElapsed);
    }


}
