package Traversal;

import Components.CompressedGraph;

import java.sql.Time;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHybridTraversal extends HybridGraphTraversal {

    private ExecutorService pool;

    public ConcurrentHybridTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes, TraversalType type) {
        super(graph, saveToMem, anchorNodes, type);
        pool = Executors.newFixedThreadPool(10);
    }


    @Override
    public void execute(){
        clearAll();
        long startTime = System.nanoTime();
        for(int anchor: getAnchorNodes()){
            pool.execute(new AnchorTraversal(anchor));
        }

        for (int start : graph.getStartPoints()) {
            pool.execute(new AnchorConcatenate(start));
        }
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished DFS sub concatenate!");
        pool.shutdown();

    }

    class AnchorTraversal implements Runnable{

        int start;
        AnchorTraversal(int i){
            start = i;
        }

        @Override
        public void run() {
            traversal(start);
        }
    }

    class AnchorConcatenate implements Runnable{
        int start;

        AnchorConcatenate(int i){
            start = i;
        }

        @Override
        public void run(){
            concatenate(start);
        }
    }

}
