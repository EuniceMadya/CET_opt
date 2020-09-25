package Traversal;

import Components.CompressedGraph;
import util.ArrayQueue;

import java.sql.Time;
import java.util.*;

public class SeqHybridGraphTraversal extends GraphTraversal {

    private int[] anchorNodes;
    private boolean[] isAnchor;
    private HashMap<Integer, Stack<int[]>> anchorPaths;

    public SeqHybridGraphTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes) {
        super(graph, saveToMem);
        this.traversalType = TraversalType.SeqHybrid;
        this.anchorNodes = anchorNodes;
        anchorPaths = new HashMap<>();
        isAnchor = new boolean[graph.getNumVertex()];
        initAnchorBool();
    }

    private void initMap() {
        for (Integer anchorNode : anchorNodes) {
            anchorPaths.put(anchorNode, new ArrayQueue<>());
        }
    }

    private void initAnchorBool(){
        Arrays.fill(isAnchor, false);
        for(int i: anchorNodes) isAnchor[i] = true;
    }

    public void setAnchorNodes(int[] anchorNodes) {
        this.anchorNodes = anchorNodes;
        initAnchorBool();
    }

    @Override
    public void execute() {
        validPaths.clear();
        anchorPaths.clear();
        initMap();

        System.out.println("Number of start points: " + graph.getStartPointNum());
        System.out.println("Number of anchor points: " + anchorNodes.length);
        System.out.println("Start DFS sub traversal!");
        long startTime = System.nanoTime();
        for (int start : anchorNodes) {
            if (graph.getNumVertex() > 500)
                System.out.println(new Time(System.currentTimeMillis()).toString() + " - start on: " + start +
                        " with degree " + graph.getNumDegree(start));
            traversal(start);
        }
        System.out.println("finished DFS sub traversal!");
        for (int start : graph.getStartPoints()) {
            BFSsubConcatenate(start);
        }
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished BFS sub traversal!");

    }

    @Override
    public void saveResults() {
        String fileName = String.format("%s-anchor%d",
                traversalType.toString(),
                anchorNodes.length - graph.getStartPointNum());
        saveResults(fileName);
    }

    @Override
    public void traversal(int start) {

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        if (graph.getNumDegree(start) != 0) DFSsubTraversal(start, stack);
            // if it is a start point and has no neighbours
        else if (graph.startContains(start)) {
            if (saveToMem) validPaths.add(getPath(stack));
            pathNum++;
        }

        // And then call BFS to copy & compute

    }

    private void DFSsubTraversal(int s, Stack<Integer> curStack) {

        if (isAnchor[s] && curStack.size() > 1 || graph.endContains(s)) {
            anchorPaths.get(curStack.firstElement()).offer(getPath(curStack));
            return;
        }

        // Recur for all the vertices adjacent to this vertex
        if (graph.endContains(s)) return;

        for (int i = graph.rowIndex[s]; i < graph.rowIndex[s + 1]; i++) {
            int edge = graph.colIndex[i];
            curStack.push(edge);
            DFSsubTraversal(edge, curStack);
            curStack.pop();
        }
    }


    private void BFSsubConcatenate(int start) {
        ArrayQueue<Stack<int[]>> queue = new ArrayQueue<>(graph.getStartPointNum());

        queue.offer(anchorPaths.get(start));

        while (!queue.isEmpty()) {
            Stack<int[]> currentPaths = queue.poll();
//            while(currentPaths.iterator().hasNext()){
//                int[]subPath = currentPaths.iterator().next();
//
            for (int[] subPath : currentPaths) {

                if (graph.endContains(subPath[subPath.length - 1])) { // probs could optimize here
                    if (saveToMem) validPaths.add(subPath);
                    pathNum++;
                    continue;
                }

                Stack<int[]> combo = new Stack<>();
                for (int[] nextList : anchorPaths.get(subPath[subPath.length - 1])) {
                //while(anchorPaths.get(subPath[subPath.length-1]).iterator().hasNext()){
                    //int[]nextList = anchorPaths.get(subPath[subPath.length-1]).iterator().next();
                    int[] newPath = new int[subPath.length - 1 + nextList.length];
                    System.arraycopy(subPath, 0, newPath, 0, subPath.length - 1);
                    System.arraycopy(nextList, 0, newPath, subPath.length - 1, nextList.length);
                    combo.push(newPath);
                }
                queue.offer(combo);
            }
        }
    }

}
