package Traversal;

import Components.CompressedGraph;
import SimpleExperiment.Concatenate;
import util.CustomDS.ArrayQueue;
import util.CustomDS.CustomObjStack;

import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;

public class DoubleAnchorTraversal extends AnchorGraphTraversal {

    private ConcatenateType firstLevel;
    private ConcatenateType secondLevel;
    public DoubleAnchorTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes,
                                 ConcatenateType firstLevel, ConcatenateType secondLevel) {
        super(graph, saveToMem, anchorNodes, firstLevel);
        this.firstLevel = firstLevel;
        this.secondLevel = secondLevel;
    }


    @Override
    public void execute(){
        clearAll();
        long startTime = System.nanoTime();
        for (int start : getAnchorNodes()) {
            if (graph.getNumVertex() > 5000)
                System.out.println(new Time(System.currentTimeMillis()).toString() + " - start on: " + start +
                        " with degree " + graph.getNumDegree(start));
            traversal(start);
        }
        System.out.println("finished traversal");

        concatenate();


        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished double leveling - -!");

    }

    private void concatenate(){
        HashMap<Integer, CustomObjStack<int[]>> newAnchorPathMap = new HashMap<>();
        // set the smallest half to be the
        for(int i = anchorNodes.length/2; i < getAnchorNodes().length; i ++){
            isAnchor[anchorNodes[i]] = false;
        }

        for(int i: anchorNodes){
            System.out.print(i + " ");
        }
    System.out.println("");
        for(int i: anchorNodes){
            if(isAnchor[i])  System.out.print(i + " ");
        }

        for(int i : graph.getStartPoints()){
            CustomObjStack <int[]>newAnchorPaths = firstConcatenate(i);
            newAnchorPathMap.put(i, newAnchorPaths);
        }
//        anchorPaths.clear();
        anchorPaths = newAnchorPathMap;
        System.out.println(anchorPaths.size());
        for(int i : graph.getStartPoints()){
            secondConcatenate(i);
        }
    }

    private CustomObjStack <int[]> firstConcatenate(int start){


        CustomObjStack<int[]>newAnchorPaths = new CustomObjStack<>();

        if(firstLevel.equals(ConcatenateType.DFS)){
            for (Object obj : anchorPaths.get(start).getAllElements()) {
                int[] startPath = (int[]) obj;
                CustomObjStack<int[]> stack = new CustomObjStack<>();
                stack.push(startPath);
                firstConcatenateDFS(startPath, stack, newAnchorPaths);
            }
        }
        else firstConcatenateBFS(start, newAnchorPaths);

        return newAnchorPaths;
    }



    private void firstConcatenateDFS(int[] s, CustomObjStack<int[]> curStack, CustomObjStack<int[]>newAnchorPaths){
        if (graph.endContains(s[s.length - 1])) {
            if (saveToMem) validPaths.add(getPathSeq(curStack));
            pathNum++;
            return;
        }

        if(isAnchor[s[s.length - 1]]) {
            newAnchorPaths.push(getPathSeq(curStack));
            return;
        }

        for (Object obj : anchorPaths.get(s[s.length - 1]).getAllElements()) { //get neighbours
            int[] nextAnchorPath = (int[]) obj;
            curStack.push(nextAnchorPath);
            firstConcatenateDFS(nextAnchorPath, curStack, newAnchorPaths);
            curStack.pop();
        }
    }

    private void firstConcatenateBFS(int start, CustomObjStack<int[]> customObjStack){
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
                if(isAnchor[subPath[subPath.length - 1]]) {
                    customObjStack.push(subPath);
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


    private  void secondConcatenate(int start){
        // second level concatenate
        if(secondLevel.equals(ConcatenateType.DFS) ){
            for (Object obj : anchorPaths.get(start).getAllElements()) {
                int[] startPath = (int[]) obj;
                CustomObjStack<int[]> stack = new CustomObjStack<>();
                stack.push(startPath);
                DFSsubConcatenate(startPath, stack);
            }
        }else{
            BFSsubConcatenate(start);
        }

    }


}
