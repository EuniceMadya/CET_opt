package Traversal;

import Components.CompressedGraph;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentAnchorTraversal extends AnchorGraphTraversal {

    public  ExecutorService pool;

    public ConcurrentAnchorTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes, ConcatenateType type) {
        super(graph, saveToMem, anchorNodes, type);
        pool = Executors.newFixedThreadPool(10);
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

        bootTasks(anchorNodes, "traversal");
        bootTasks(graph.getStartPoints().stream().mapToInt(i->i).toArray(), "concatenate");

        System.out.println("Start thread pool");
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished sub concatenate!");

    }

    private void bootTasks(int[] nodes, String operation){
        Collection<Callable<Object>> tasks = new ArrayList<>();
        System.out.println("Start tasks " + operation);
        System.out.println(Arrays.toString(nodes));
        for(int node: nodes) {
            tasks.add(new AnchorTask(node, operation));
        }
        System.out.println("Put into the pool for: " + operation);
        try {
            pool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done all tasks for " + operation);
        tasks.clear();

    }

    class AnchorTask implements Callable<Object> {

        int start;
        String operation;
        AnchorTask(int i, String operation){
            this.operation = operation;
            start = i;
        }

        @Override
        public Object call() {
            if(operation.equalsIgnoreCase("traversal"))
                traversal(start);
            else concatenate(start);
            return null;
        }
    }


}
