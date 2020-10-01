package Traversal;

import Components.CompressedGraph;

import java.util.Stack;

public class HybridDFSDFSTraversal extends SeqHybridGraphTraversal {

    public HybridDFSDFSTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes) {
        super(graph, saveToMem, anchorNodes);
    }

    public void concatenate(int start) {
        for (int[] startPath : anchorPaths.get(start)) {
            Stack<int[]> stack = new Stack<>();
            stack.push(startPath);
            DFSsubConcatenate(startPath, stack);
        }
    }

    private void DFSsubConcatenate(int[] s, Stack<int[]> curStack) {

        if (graph.endContains(s[s.length - 1])) {
            if (saveToMem) validPaths.add(getPathSeq(curStack));
            pathNum++;
            return;
        }

        for (int[] nextAnchorPath : anchorPaths.get(s[s.length - 1])) { //get neighbours
            curStack.push(nextAnchorPath);
            DFSsubConcatenate(nextAnchorPath, curStack);
            curStack.pop();
        }

    }

    private int[] getPathSeq(Stack<int[]> stack) {
        int length = 0;
        for (int[] s : stack) {
            if (!graph.endContains(s[s.length - 1]))
                length += s.length - 1;
            else length += s.length;
        }

        int[] pathArray = new int[length];

        length = 0;
        for (int[] s : stack) {
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
