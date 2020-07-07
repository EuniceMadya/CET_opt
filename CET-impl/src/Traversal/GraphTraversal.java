package Traversal;

import Components.Graph;
import Components.Path;
import Components.Vertex;

import java.sql.Timestamp;

public abstract class GraphTraversal implements Runnable {
    Graph graph;
    Timestamp window;

    public GraphTraversal(Graph graph, Timestamp windowSize) {
        this.graph = graph;
        this.window = windowSize;
        //TODO: change it later to actual window size
        this.window = new Timestamp(Long.MAX_VALUE);

    }


    public boolean identifyPattern(Path path){
        if(path == null){
            return false;
        }
        Vertex start =  graph.getVertex(path.getPathNodes().get(0));
        Vertex end = graph.getVertex(path.getPathNodes().get(path.getPathNodes().size() - 1));
        Timestamp timeLap = new Timestamp(end.getTime().getTime() - start.getTime().getTime());

        path.setSatisfied(timeLap.getTime()< window.getTime());


        return path.isSatisfied();
    }

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
