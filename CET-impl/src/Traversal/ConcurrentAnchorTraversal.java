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

        bootTasks(anchorNodes, "traversal");
        bootTasks(graph.getStartPoints().stream().mapToInt(i->i).toArray(), "concatenate");


        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished sub concatenate!");

    }

    private void bootTasks(int[] nodes, String operation){
        Collection<Callable<Object>> tasks = new ArrayList<>();
        for(int node: nodes) {
            tasks.add(new AnchorTask(node, operation));
            if (operation.equalsIgnoreCase("traversal"))
//                if (graph.getNumVertex() > 5000)
                System.out.println(new Time(System.currentTimeMillis()).toString() + " - put in: " + node +
                        " with degree " + graph.getNumDegree(node));
        }
        try {
            pool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done one boot");

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
