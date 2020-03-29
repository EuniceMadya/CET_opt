package Traversal;

import java.sql.Time;
import java.util.List;

public abstract class GraphTraversal implements Runnable{
    Graph graph;
    Time window;

    public GraphTraversal(Graph graph, Time windowSize){
        this.graph = graph;
        this.window = windowSize;
    }


    public abstract List<Integer> findPattern();

    public abstract void traversal();



}
