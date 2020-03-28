package Traversal;

import java.time.LocalDateTime;

public abstract class GraphTraversal implements Runnable{
    Graph graph;
    LocalDateTime window;

    public GraphTraversal(Graph graph, LocalDateTime windowSize){
        this.graph = graph;
        this.window = windowSize;
    }

    @Override
    public void run() {

    }



}
