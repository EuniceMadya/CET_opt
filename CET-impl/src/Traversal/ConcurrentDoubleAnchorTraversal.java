package Traversal;

import Components.CompressedGraph;
import util.CustomDS.CustomObjStack;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentDoubleAnchorTraversal extends DoubleAnchorTraversal {

    public ExecutorService pool;


    public ConcurrentDoubleAnchorTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes,
                                           ConcatenateType firstLevel, ConcatenateType secondLevel) {
        super(graph, saveToMem, anchorNodes, firstLevel, secondLevel);
        pool = Executors.newFixedThreadPool(20);

    }

    @Override
    public void saveResults() {
        String fileName = String.format("%s-anchor%d-concurrent-double",
                traversalType.toString(),
                anchorNodes.length - graph.getStartPointNum());
        saveResults(fileName);
    }

    @Override
    public void execute(){
        clearAll();
        System.gc();
        long startTime = System.nanoTime();
        bootTasks(anchorNodes, "traversal");

        reduceAnchorNodes();
        bootTasks(anchorNodes, "first");
        bootTasks(graph.getStartPoints().stream().mapToInt(i->i).toArray(), "second");

        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished double leveling - -!");

        System.out.println("path num: " + pathNum);
    }



    private void bootTasks(int[] nodes, String operation){
        Collection<Callable<Object>> tasks = new ArrayList<>();
        for(int node: nodes) {
            tasks.add(new AnchorTask(node, operation));

        }
        List<Future<Object>> results = new ArrayList<>();
        try {
             results = pool.invokeAll(tasks);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(operation.equalsIgnoreCase("first")){
            for(int i: anchorNodes){
                if(!isAnchor[i]) anchorPaths.remove(i);
            }
            for(Future<Object> future: results){
                try {
                    CustomObjStack<int[]> newAnchorPaths = (CustomObjStack<int[]>) future.get();
                    if(newAnchorPaths.size() > 0)
                        anchorPaths.replace(newAnchorPaths.peek()[0], newAnchorPaths);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



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
            Object object = null;
            if(operation.equalsIgnoreCase("traversal"))
                traversal(start);
            else if(operation.equalsIgnoreCase("first"))
                object = firstConcatenate(start);
            else secondConcatenate(start);
            return object;
        }
    }

}
