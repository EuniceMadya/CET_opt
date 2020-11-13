package Traversal;

import Components.CompressedGraph;
import util.CustomDS.ArrayQueue;
import util.CustomDS.CustomObjStack;

import java.sql.Time;


public class DoubleAnchorTraversal extends AnchorGraphTraversal {

    public ConcatenateType firstLevel;
    public ConcatenateType secondLevel;
    String reduceAnchorType;
    public DoubleAnchorTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes,
                                 ConcatenateType firstLevel, ConcatenateType secondLevel, String doubleType) {
        super(graph, saveToMem, anchorNodes, firstLevel);
        this.firstLevel = firstLevel;
        this.secondLevel = secondLevel;
        traversalType = TraversalType.DoubleAnchor;
        reduceAnchorType = doubleType;
    }


    @Override
    public void execute(){
        clearAll();
        System.gc();
        long startTime = System.nanoTime();
        for (int start : getAnchorNodes()) {
            if (graph.getNumVertex() > 5000)
                System.out.println(new Time(System.currentTimeMillis()).toString() + " - start on: " + start +
                        " with degree " + graph.getNumDegree(start));
            traversal(start);
        }

        concatenate();
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(new Time(System.currentTimeMillis()).toString() + " - finished double leveling - -!");

        System.out.println("path num: " + pathNum);
    }

    void reduceHalfAnchorNodes(){
        // set the smallest half to be non-anchor
        for(int i = anchorNodes.length - 1;
            i >anchorNodes.length -
                    (anchorNodes.length - graph.getStartPointNum())/2; i --){
            if(!graph.startContains(anchorNodes[i])){
                isAnchor[anchorNodes[i]] = false;
            }
        }

    }


    void reduceMostAnchorNodes(){
        // set it to remain only the largest one
        int largestDegree = 0;
        for(int i : anchorNodes){
            if(graph.startContains(i)) continue;
            if(largestDegree < graph.getNumDegree(i))
                largestDegree = graph.getNumDegree(i);
            if (graph.getNumDegree(i) < largestDegree)
                isAnchor[i] = false;
        }

    }

    void reduceAnchorNodes(){
        if(reduceAnchorType.contains("largest")) reduceMostAnchorNodes();
        else reduceHalfAnchorNodes();
    }

     void concatenate(){
        reduceAnchorNodes();

        for(int i : anchorNodes){
            CustomObjStack <int[]>newAnchorPaths = firstConcatenate(i);
            anchorPaths.replace(i, newAnchorPaths);
        }

        System.out.println("first concatenate finished");

        for(int i: anchorNodes){
            if(!isAnchor[i]) anchorPaths.remove(i);
        }


        for(int i : graph.getStartPoints()){
            secondConcatenate(i);
        }
    }

    CustomObjStack <int[]> firstConcatenate(int start){

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

        if(graph.startContains(((int[])curStack.firstElement())[0]) && graph.endContains(s[s.length - 1])) {
            if (saveToMem) validPaths.add(getPathSeq(curStack));
            return;
        }

        if(isAnchor[s[s.length - 1]] || graph.endContains(s[s.length - 1])) {
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

                if(graph.startContains(subPath[0]) && graph.endContains(subPath[subPath.length - 1])) {
                    if (saveToMem) validPaths.add(subPath);
                    pathNum ++;
                    continue;
                }
                if(isAnchor[subPath[subPath.length - 1]]
                        || graph.endContains(subPath[subPath.length - 1])) {
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


    void secondConcatenate(int start){
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
