package Traversal;

import Components.CompressedGraph;
import util.CustomDS.ArrayQueue;

import java.util.ArrayList;
import java.util.Arrays;

public class HybridDFSBFSTraversal extends SeqHybridGraphTraversal {

    public HybridDFSBFSTraversal(CompressedGraph graph, boolean saveToMem, int[] anchorNodes) {
        super(graph, saveToMem, anchorNodes);
    }

    public void concatenate(int start) {
        BFSsubConcatenate(start);
    }

    private void BFSsubConcatenate(int start) {
        ArrayQueue<ArrayList<int[]>> queue = new ArrayQueue<>(graph.getStartPointNum());

        queue.offer(anchorPaths.get(start));

        while (!queue.isEmpty()) {
            ArrayList<int[]> currentPaths = queue.poll();
            for (int[] subPath : currentPaths) {
                if (graph.endContains(subPath[subPath.length - 1])) { // probs could optimize here
                    if (saveToMem) validPaths.add(subPath);
                    pathNum++;
                    continue;
                }

                ArrayList<int[]> combo = new ArrayList<>();
                for (int[] nextList : anchorPaths.get(subPath[subPath.length - 1])) {

                    int[] newPath = new int[subPath.length - 1 + nextList.length];
                    System.arraycopy(subPath, 0, newPath, 0, subPath.length - 1);
                    System.arraycopy(nextList, 0, newPath, subPath.length - 1, nextList.length);

                    System.out.println(Arrays.toString(newPath));

                    combo.add(newPath);
                }
                queue.offer(combo);
            }
            currentPaths = null; // let garbage collection handle it
        }
    }


}
