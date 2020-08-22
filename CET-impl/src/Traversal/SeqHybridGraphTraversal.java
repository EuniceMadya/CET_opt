package Traversal;

import Components.Graph;

import java.sql.Timestamp;
import java.util.*;

public class SeqHybridGraphTraversal extends GraphTraversal {

    List<Integer> anchorNodes;
    HashMap<Integer, ArrayList<ArrayList<Integer>>> anchorPaths;

    public SeqHybridGraphTraversal(Graph graph, Timestamp windowSize, ArrayList <Integer> anchorNodes){
        super(graph, windowSize);
        this.traversalType = TraversalType.SeqHybrid;
        this.anchorNodes = anchorNodes;
        anchorPaths = new HashMap<>();
        initMap();
    }

    public void initMap(){
        for(Integer anchorNode: anchorNodes){
            anchorPaths.put(anchorNode, new ArrayList<>());
        }
    }

    @Override
    public void execute(){
        long startTime = System.nanoTime();
        for (int start : anchorNodes) {
            traversal(start);
            System.out.println( start);
        }
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        showResults(traversalType.toString());
        System.out.println(this.getClass().getName() + "Execution in nanoseconds  : " + timeElapsed);
    }

    @Override
    public void traversal(int start) {
        boolean[] visited = new boolean[graph.getNumVertex()];
        Arrays.fill(visited, false);

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        if (graph.getVertex(start).getNeighbours().size() != 0 && graph.getStartPoints().contains(start)) DFSsubTraversal(start, visited, stack);
        else validPaths.add(new ArrayList<>(stack));
        // And then call BFS to copy & compute

    }

    public void DFSsubTraversal(int s, boolean[] visited, Stack curStack){
        visited[s] = true;

        if (anchorNodes.contains(s) && curStack.size() > 1) {
            ArrayList<Integer> listPath = new ArrayList<>(curStack);
            if (identifyPattern(listPath)) {
                anchorPaths.get(s).add(listPath);
                validPaths.add(new ArrayList<>(curStack));
            }
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        List<Integer> edges = graph.getEdges(s);
        for (Integer edge : edges) {
            curStack.push(edge);
            identifyPattern(new ArrayList<>(curStack));
            DFSsubTraversal(edge, visited, curStack);
            curStack.pop();
        }
    }
}
