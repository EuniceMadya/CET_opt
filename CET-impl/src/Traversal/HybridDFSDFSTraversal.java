package Traversal;

import Components.CompressedGraph;

import java.util.ArrayList;
import java.util.Stack;

public class HybridDFSDFSTraversal extends SeqHybridGraphTraversal {

    public HybridDFSDFSTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes) {
        super(graph, saveToMem, anchorNodes);
    }

    public void concatenate(int start){
        for(int[] startPath: anchorPaths.get(start)){
            Stack<int[]> stack = new Stack<>();
            stack.push(startPath);
            DFSsubConcatenate(startPath, stack );
        }
    }

    private void DFSsubConcatenate(int[] s, Stack<int[]> curStack){

        if(graph.endContains(s[s.length - 1])){
            if(saveToMem) validPaths.add(getPathSeq(curStack));
            pathNum ++;
            return;
        }

        for(int[] nextAnchorPath: anchorPaths.get(s[s.length - 1])){ //get neighbours
            curStack.push(nextAnchorPath);
            DFSsubConcatenate(nextAnchorPath, curStack);
            curStack.pop();
        }

    }

    private int[] getPathSeq(Stack <int[]> stack){
        ArrayList<Integer> path = new ArrayList<>();
        for(int[] s: stack){
            for(int i = 0; i< s.length; i ++){
                if(i == s.length - 1 && !graph.endContains(s[i]))
                    continue;
                path.add(s[i]);
            }
        }
        int[] pathArray = new int[path.size()];
        for(int i = 0; i < path.size(); i ++){
            pathArray[i] = path.get(i);
        }
        path = null;
        return pathArray;

    }
}
