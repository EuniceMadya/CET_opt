package Traversal;

import Components.CompressedGraph;

import java.util.Iterator;
import java.util.Stack;

public class M_CETGraphTraversal extends GraphTraversal {

    public M_CETGraphTraversal(CompressedGraph graph, boolean saveToMem){
        super(graph, saveToMem);
        traversalType = TraversalType.M_CET;

    }

    @Override
    public void traversal(int i) {
        traversal(i, new Stack<Integer>());
    }

    private void traversal(int curNode, Stack<Integer> currentSeq) {
        currentSeq.push(curNode);

        if (graph.getEndPoints().contains(curNode)) {
            Stack<Integer> resultPath = new Stack<>();
            Iterator<Integer> iter = currentSeq.iterator();
            while (iter.hasNext()) {
                int node = iter.next();
                resultPath.push(node);
            }
            if(saveToMem) validPaths.add(getPath(resultPath));
            currentSeq.clear();
        } else {
            for (int i = graph.rowIndex[curNode]; i < graph.rowIndex[curNode + 1]; i++) {
                int neighbour = graph.colIndex[i];
                traversal(neighbour, currentSeq);
            }
        }


    }
}
