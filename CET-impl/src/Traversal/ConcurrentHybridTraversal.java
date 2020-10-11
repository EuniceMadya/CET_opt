package Traversal;

import Components.CompressedGraph;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHybridTraversal extends HybridGraphTraversal {

    public  ExecutorService pool;

    public ConcurrentHybridTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes, TraversalType type) {
        super(graph, saveToMem, anchorNodes, type);
        pool = Executors.newFixedThreadPool(10);
    }


    @Override
    public void execute()  {
        clearAll();
        long startTime = System.nanoTime();
        Collection<Callable<Object>> traversalTasks = new ArrayList<>();

        for(int anchor: getAnchorNodes()) traversalTasks.add(new AnchorTraversal(anchor));

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
        public Object call() throws Exception {
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
        public Object call() throws Exception {
            concatenate(start);
            return null;
        }
    }

}
