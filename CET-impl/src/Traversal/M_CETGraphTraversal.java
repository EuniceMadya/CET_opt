package Traversal;

import Components.CompressedGraph;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class M_CETGraphTraversal extends GraphTraversal {
    public M_CETGraphTraversal(CompressedGraph graph, Timestamp windowSize) {
        super(graph, windowSize);
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
            validPaths.add(new ArrayList<>(resultPath));
            currentSeq.clear();
        } else {
            for (int i = graph.rowIndex[curNode]; i < graph.rowIndex[curNode + 1]; i++) {
                int neighbour = graph.colIndex[i];
                traversal(neighbour, currentSeq);
            }
        }


    }
}
