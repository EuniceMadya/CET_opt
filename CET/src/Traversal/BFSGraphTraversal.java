package Traversal;

import java.sql.Time;
import java.util.List;
import java.util.PriorityQueue;

public class BFSGraphTraversal extends GraphTraversal{

    public BFSGraphTraversal(Graph graph, Time windowSize){
        super(graph, windowSize);

    }

    @Override
    public List<Integer> findPattern() {
        return null;
    }

    // BFS traversal
    @Override
    public void traversal(int start) {
        boolean [] visited = new boolean [graph.numVertex];
        int visitNum = 1;
        PriorityQueue <Integer> queue = new PriorityQueue<>();

        for(int i = 0; i < visited.length; i ++ ){
            visited[i] = false;
        }

        visited[start] = true;
        queue.add(start);

        while(!queue.isEmpty()){
            int current = queue.poll();
            System.out.println("visiting: " + current);

        }
    }




 



    @Override
    public void run() {
        traversal(0);

    }
}
