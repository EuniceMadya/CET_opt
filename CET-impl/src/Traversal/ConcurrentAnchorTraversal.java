package Traversal;

import Components.CompressedGraph;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentAnchorTraversal extends AnchorGraphTraversal {

    public  ExecutorService pool;

    public ConcurrentAnchorTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes, ConcatenateType type) {
        super(graph, saveToMem, anchorNodes, type);
        pool = Executors.newFixedThreadPool(20);
    }

    @Override
    public void saveResults() {
        String fileName = String.format("%s-anchor%d-concurrent",
                traversalType.toString(),
                anchorNodes.length - graph.getStartPointNum());
        saveResults(fileName);
    }


    @Override
    public void execute()  {
        clearAll();
        long startTime = System.nanoTime();
        Collection<Callable<Object>> traversalTasks = new ArrayList<>();

        for(int anchor: anchorNodes) {
            traversalTasks.add(new AnchorTraversal(anchor));
            if (graph.getNumVertex() > 5000)
                System.out.println(new Time(System.currentTimeMillis()).toString() + " - start on: " + anchor +
                        " with degree " + graph.getNumDegree(anchor));
        }

        try {
            pool.invokeAll(traversalTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Collection<Callable<Object>> concatenateTasks = new ArrayList<>();
        for (int start : graph.getStartPoints()) concatenateTasks.add(new AnchorConcatenate(start));
        try {
            pool.invokeAll(concatenateTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished sub concatenate!");


    }

    class AnchorTraversal implements Callable<Object> {

        int start;
        AnchorTraversal(int i){
            start = i;
        }


        @Override
        public Object call() {
            traversal(start);
            return null;
        }
    }

    class AnchorConcatenate implements Callable<Object>{
        int start;

        AnchorConcatenate(int i){
            start = i;
        }

        @Override
        public Object call() {
            concatenate(start);
            return null;
        }
    }


}
