package Traversal;

import Components.CompressedGraph;
import util.CustomDS.CustomObjStack;

import java.util.Stack;

public class HybridDFSDFSTraversal extends SeqHybridGraphTraversal {

    public HybridDFSDFSTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes) {
        super(graph, saveToMem, anchorNodes);
    }

    public void concatenate(int start) {
        for (Object obj : anchorPaths.get(start).getAllElements()) {
            int[] startPath = (int[]) obj;
            CustomObjStack<int[]> stack = new CustomObjStack<>();
            stack.push(startPath);
            DFSsubConcatenate(startPath, stack);
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
        for ( Object object : stack.getAllElements()) {
            int []s = (int[]) object;
            if (!graph.endContains(s[s.length - 1]))
                length += s.length - 1;
            else length += s.length;
        }

        int[] pathArray = new int[length];

        length = 0;
        for (Object obj : stack.getAllElements()) {
            int[] s = (int[])obj;
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
