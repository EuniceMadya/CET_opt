package Traversal;

import Components.Graph;
import Components.Path;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Stack;

public class M_CETGraphTraversal extends GraphTraversal {
    public M_CETGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
        traversalType = TraversalType.M_CET;
    }

    @Override
    public void traversal(int i) {
        traversal(i, new Stack<Integer>());
    }

    private void traversal(int curNode, Stack<Integer> currentSeq) {
        currentSeq.push(curNode);

        if(graph.getEndPoints().contains(curNode)){
            Path resultPath = new Path();
            Iterator<Integer> iter = currentSeq.iterator();
            while(iter.hasNext()){
                int node = iter.next();
                resultPath.addNode(node);
            }
            validPaths.add(resultPath);
            currentSeq.clear();
        }else{
            for(int neighbour: graph.getEdges(curNode) )
                traversal(neighbour, currentSeq);
        }


    }
}
