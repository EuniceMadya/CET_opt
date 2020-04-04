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

        while(visitNum != graph.numVertex){
            if(queue.isEmpty()){
                int unvisited = findUnvisit(visited) ;
                if(unvisited ==-1){
                    return;
                }
                queue.add(unvisited);
                visited[unvisited] = true;
                visitNum += 1;
            }
            int current = queue.poll();
            System.out.println("visiting: " + current);
            for(Integer toVisit: graph.getEdges(current)){
                if(!visited[toVisit]){
                    queue.add(toVisit);
                    visited[toVisit] = true;
                    visitNum += 1;
                    System.out.println("adding: " + toVisit);
                }
            }
        }
    }

    public int findUnvisit(boolean [] visited){
        for(int i = 0; i < visited.length; i ++){
            if(!visited[i]) return i;
        }
        return -1;
    }

    @Override
    public void run() {

    }
}
