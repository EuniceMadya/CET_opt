package Traversal;

import Components.Graph;
import util.ArrayQueue;

import java.sql.Timestamp;
import java.util.*;

public class SeqHybridGraphTraversal extends GraphTraversal {

    List<Integer> anchorNodes;
    HashMap<Integer, ArrayList<ArrayList<Integer>>> anchorPaths;

    public SeqHybridGraphTraversal(Graph graph, Timestamp windowSize, ArrayList<Integer> anchorNodes) {
        super(graph, windowSize);
        this.traversalType = TraversalType.SeqHybrid;
        this.anchorNodes = anchorNodes;
        anchorPaths = new HashMap<>();
    }

    public void initMap() {
        for (Integer anchorNode : anchorNodes) {
            anchorPaths.put(anchorNode, new ArrayList<>());
        }
    }

    @Override
    public void execute() {
        validPaths.clear();
        anchorPaths.clear();
        initMap();
        long startTime = System.nanoTime();
        for (int start : anchorNodes) {
            traversal(start);
        }
        for(int start: graph.getStartPoints()){
            BFSsubConcatenate(start);
        }
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        
        String fileName = String.format("%s-anchor%d",
                traversalType.toString(),
                anchorNodes.size()-graph.getStartPoints().size());

        showResults(fileName);

    }

    @Override
    public void traversal(int start) {
        boolean[] visited = new boolean[graph.getNumVertex()];
        Arrays.fill(visited, false);

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        if (graph.getVertex(start).getNeighbours().size() != 0)
            DFSsubTraversal(start, visited, stack);
        else if (graph.getStartPoints().contains(start)) validPaths.add(new ArrayList<>(stack));

        // And then call BFS to copy & compute

    }

    public void DFSsubTraversal(int s, boolean[] visited, Stack curStack) {
        visited[s] = true;


        if (anchorNodes.contains(s) && curStack.size() > 1 || graph.getEndPoints().contains(s)) {
            ArrayList<Integer> listPath = new ArrayList<>(curStack);
            anchorPaths.get(curStack.firstElement()).add(listPath);
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        List<Integer> edges = graph.getEdges(s);
        if(edges.size() == 0) return;

        for (Integer edge : edges) {
            curStack.push(edge);
            DFSsubTraversal(edge, visited, curStack);
            curStack.pop();
        }
    }


    public void BFSsubConcatenate(int start){
        ArrayQueue<Stack<ArrayList<ArrayList<Integer>>>> queue = new ArrayQueue();
        Stack<ArrayList<ArrayList<Integer>>> superPaths = new Stack<>();
        superPaths.add(anchorPaths.get(start));
        queue.offer(superPaths);

        while(!queue.isEmpty()){
            Stack<ArrayList<ArrayList<Integer>>> currentPaths = queue.poll();
            for(List<Integer> subPath: currentPaths.peek()){
                Stack<ArrayList<ArrayList<Integer>>> newPathStack = new Stack<>();
                if (anchorPaths.get(subPath.get(subPath.size() - 1)) == null) {
                    validPaths.add(new ArrayList<>(subPath));
                    continue;
                }
                ArrayList<ArrayList<Integer>> combo = new ArrayList<>();
                for (List<Integer> nextList: anchorPaths.get(subPath.get(subPath.size() - 1))){
                    ArrayList<Integer> newPath = new ArrayList<>(subPath);
                    newPath.remove(newPath.size()-1);
                    newPath.addAll(nextList);
                    combo.add(newPath);
                }
                newPathStack.push(combo);
                queue.offer(newPathStack);
            }
        }
    }

}
