package Traversal;

import Components.CompressedGraph;
import util.CustomDS.ArrayQueue;
import util.CustomDS.CustomIntStack;
import util.CustomDS.CustomObjStack;

import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;

public class HybridGraphTraversal extends GraphTraversal {

    private int[] anchorNodes;
    private boolean[] isAnchor;
    HashMap<Integer, CustomObjStack<int[]>> anchorPaths;

    public HybridGraphTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes, TraversalType type) {
        super(graph, saveToMem);
        this.traversalType = type;
        this.anchorNodes = anchorNodes;
        anchorPaths = new HashMap<>();
        isAnchor = new boolean[graph.getNumVertex()];
    }

    private void initMap() {
        for (Integer anchorNode : anchorNodes) {
            anchorPaths.put(anchorNode, new CustomObjStack<>());
        }
    }

    private void initAnchorBool() {
        Arrays.fill(isAnchor, false);
        for (int i : anchorNodes) isAnchor[i] = true;
    }

    public void setAnchorNodes(int[] anchorNodes) {
        this.anchorNodes = anchorNodes;
        initAnchorBool();
    }

    int[] getAnchorNodes(){
        return anchorNodes;
    }

    @Override
    void clearAll(){
        validPaths.clear();
        anchorPaths.clear();
        initMap();
        pathNum = 0;
    }

    @Override
    public void execute() {
        clearAll();

        System.out.println("Number of start points: " + graph.getStartPointNum());
        System.out.println("Number of anchor points: " + anchorNodes.length);
//        System.out.println("Start DFS sub traversal!");
        long startTime = System.nanoTime();
        for (int start : anchorNodes) {
            if (graph.getNumVertex() > 5000)
                System.out.println(new Time(System.currentTimeMillis()).toString() + " - start on: " + start +
                        " with degree " + graph.getNumDegree(start));
            traversal(start);
        }

        for (int start : graph.getStartPoints()) {
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

        CustomIntStack stack = new CustomIntStack();
        stack.push(start);
        if (graph.getNumDegree(start) != 0) DFSsubTraversal(start, stack);
            // if it is a start point and has no neighbours
        else if (graph.startContains(start)) {
            if (saveToMem) validPaths.add(stack.getAllElements());
            pathNum++;
        }

    }


    void concatenate(int start){
        if(traversalType.equals(TraversalType.SeqHybridDFSBFS)) BFSsubConcatenate(start);
        if(traversalType.equals(TraversalType.SeqHybridDFSDFS)){
            for (Object obj : anchorPaths.get(start).getAllElements()) {
                int[] startPath = (int[]) obj;
                CustomObjStack<int[]> stack = new CustomObjStack<>();
                stack.push(startPath);
                DFSsubConcatenate(startPath, stack);
            }
        }
    }

    private void DFSsubTraversal(int s, CustomIntStack curStack) {

        if (isAnchor[s] && curStack.size() > 1 || graph.endContains(s)) {
            anchorPaths.get(curStack.firstElement()).push(curStack.getAllElements());
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
        ArrayQueue<CustomObjStack<int[]>> queue = new ArrayQueue<>(graph.getStartPointNum());

        queue.offer(anchorPaths.get(start));

        while (!queue.isEmpty()) {
            CustomObjStack<int[]> currentPaths = queue.poll();
            for (Object obj : currentPaths.getAllElements()) {
                int[] subPath = (int[]) obj;
                if (graph.endContains(subPath[subPath.length - 1])) { // probs could optimize here
                    if (saveToMem) validPaths.add(subPath);
                    pathNum++;
                    continue;
                }

                CustomObjStack<int[]> combo = new CustomObjStack<>();
                for (Object object : anchorPaths.get(subPath[subPath.length - 1]).getAllElements()) {
                    int[] nextList = (int[]) object;
                    int[] newPath = new int[subPath.length - 1 + nextList.length];
                    System.arraycopy(subPath, 0, newPath, 0, subPath.length - 1);
                    System.arraycopy(nextList, 0, newPath, subPath.length - 1, nextList.length);

                    combo.push(newPath);
                }
                queue.offer(combo);
            }
            currentPaths = null; // let garbage collection deal with it
        }
    }


    private void DFSsubConcatenate(int[] s, CustomObjStack<int[]> curStack) {

        if (graph.endContains(s[s.length - 1])) {
            if (saveToMem) validPaths.add(getPathSeq(curStack));
            pathNum++;
            return;
        }

        for (Object obj : anchorPaths.get(s[s.length - 1]).getAllElements()) { //get neighbours
            int[] nextAnchorPath = (int[]) obj;
            curStack.push(nextAnchorPath);
            DFSsubConcatenate(nextAnchorPath, curStack);
            curStack.pop();
        }

    }

    private int[] getPathSeq(CustomObjStack<int[]> stack) {
        int length = 0;
        for (Object object : stack.getAllElements()) {
            int[] s = (int[]) object;
            if (!graph.endContains(s[s.length - 1]))
                length += s.length - 1;
            else length += s.length;
        }

        int[] pathArray = new int[length];

        length = 0;
        for (Object obj : stack.getAllElements()) {
            int[] s = (int[]) obj;
            if (!graph.endContains(s[s.length - 1])) {
                System.arraycopy(s, 0, pathArray, length, s.length - 1);
                length += s.length - 1;
            } else {
                System.arraycopy(s, 0, pathArray, length, s.length);
                length += s.length;
            }
        }

        return pathArray;

    }



}
