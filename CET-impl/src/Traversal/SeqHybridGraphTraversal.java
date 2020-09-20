package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;

import java.util.*;
import java.util.stream.IntStream;

public class SeqHybridGraphTraversal extends GraphTraversal {

    private int[] anchorNodes;
    private HashMap<Integer, ArrayList<int[]>> anchorPaths;

    public SeqHybridGraphTraversal(CompressedGraph graph,boolean saveToMem, int[] anchorNodes) {
        super(graph, saveToMem);
        this.traversalType = TraversalType.SeqHybrid;
        this.anchorNodes = anchorNodes;
        anchorPaths = new HashMap<>();
    }

    private void initMap() {
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
        System.out.println("finished DFS sub traversal!");
        for (int start : graph.getStartPoints()) {
            BFSsubConcatenate(start);
        }
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;

    }

    @Override
    public void saveResults() {
        String fileName = String.format("%s-anchor%d",
                traversalType.toString(),
                anchorNodes.length - graph.getStartPoints().size());
        saveResults(fileName);
    }

    @Override
    public void traversal(int start) {
        boolean[] visited = new boolean[graph.getNumVertex()];
        Arrays.fill(visited, false);

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        if (graph.getNumDegree(start) != 0)
            DFSsubTraversal(start, visited, stack);
        // if it is a start point and has no neighbours
        else if (graph.getStartPoints().contains(start)) {
            if(saveToMem) validPaths.add(getPath(stack));
            pathNum ++ ;
        }

        // And then call BFS to copy & compute

    }

    private void DFSsubTraversal(int s, boolean[] visited, Stack<Integer> curStack) {
        visited[s] = true;


        if (IntStream.of(anchorNodes).anyMatch(x -> x == s) && curStack.size() > 1 || graph.getEndPoints().contains(s)) {
            anchorPaths.get(curStack.firstElement()).add(getPath(curStack));
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        if (graph.getRowIndex()[s + 1] - graph.getRowIndex()[s] == 0) return;

        for (int i = graph.rowIndex[s]; i < graph.rowIndex[s + 1]; i++) {
            int edge = graph.getColIndex()[i];
            curStack.push(edge);
            DFSsubTraversal(edge, visited, curStack);
            curStack.pop();
        }
    }


    private void BFSsubConcatenate(int start) {
        ArrayQueue<Stack<ArrayList<int []>>> queue = new ArrayQueue<>(graph.getStartPoints().size());

        Stack<ArrayList<int[]>> superPaths = new Stack<>();

        // Add initial paths that
        superPaths.add(anchorPaths.get(start));
        queue.offer(superPaths);

        while (!queue.isEmpty()) {
            Stack<ArrayList<int[]>> currentPaths = queue.poll();
            for (int[] subPath : currentPaths.peek()) {
                Stack<ArrayList<int[]>> newPathStack = new Stack<>();
                if (anchorPaths.get(subPath[subPath.length - 1]) == null) { // probs could optimize here
                    if(saveToMem) validPaths.add(subPath);
                    pathNum ++;
                    continue;
                }
                ArrayList<int[]> combo = new ArrayList<>();
                for (int[] nextList : anchorPaths.get(subPath[subPath.length - 1])){
                    int[] newPath = new int[subPath.length - 1 + nextList.length];
                    System.arraycopy(subPath,0,newPath, 0, subPath.length - 1);
                    System.arraycopy(nextList, 0, newPath, subPath.length - 1, nextList.length);
                    combo.add(newPath);
                }
                newPathStack.push(combo);
                queue.offer(newPathStack);
            }
        }
    }

}
