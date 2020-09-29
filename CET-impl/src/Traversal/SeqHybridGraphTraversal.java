package Traversal;

import Components.CompressedGraph;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public abstract class SeqHybridGraphTraversal extends GraphTraversal {

    private int[] anchorNodes;
    private boolean[] isAnchor;
    HashMap<Integer, ArrayList<int[]>> anchorPaths;

    public SeqHybridGraphTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes) {
        super(graph, saveToMem);
        this.traversalType = TraversalType.SeqHybrid;
        this.anchorNodes = anchorNodes;
        anchorPaths = new HashMap<>();
        isAnchor = new boolean[graph.getNumVertex()];
    }

    private void initMap() {
        for (Integer anchorNode : anchorNodes) {
            anchorPaths.put(anchorNode, new ArrayList<>());
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
        pathNum = 0;
        System.out.println("Number of start points: " + graph.getStartPointNum());
        System.out.println("Number of anchor points: " + anchorNodes.length);
        System.out.println("Start DFS sub traversal!");
        long startTime = System.nanoTime();
        for (int start : anchorNodes) {
            if (graph.getNumVertex() > 5000)
                System.out.println(new Time(System.currentTimeMillis()).toString() + " - start on: " + start +
                        " with degree " + graph.getNumDegree(start));
            traversal(start);
        }
        System.out.println("finished DFS sub traversal!");

        for(int start: graph.getStartPoints()){
//            System.out.println("start concatenate for: "+ start);
            concatenate(start);
        }

        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished DFS sub concatenate!");

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


    abstract void concatenate(int start);

    private void DFSsubTraversal(int s, Stack<Integer> curStack) {

        if (isAnchor[s] && curStack.size() > 1 || graph.endContains(s)) {
            anchorPaths.get(curStack.firstElement()).add(getPath(curStack));
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


}
