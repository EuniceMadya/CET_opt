package Traversal;

import Components.CompressedGraph;

import java.util.ArrayList;
import java.util.HashMap;

public class T_CETGraphTraversal extends GraphTraversal {

    private ArrayList<ArrayList<Integer>>[] paths;

    public T_CETGraphTraversal(CompressedGraph graph, boolean saveToMem) {
        super(graph, saveToMem);
        traversalType = TraversalType.T_CET;
        paths = new ArrayList[graph.getNumVertex()];
        for (int i = 0; i < paths.length; i++) paths[i] = new ArrayList<>();

    }

    @Override
    public void execute() {
        long startTime = System.nanoTime();
        traversal(0);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("num of paths:" + validPaths.size());
        showResults(traversalType.toString());
        System.out.println("time for T_CET: " + timeElapsed);
    }

    @Override
    public void traversal(int i) {
        System.out.println(graph.getStartPoints().size());
        T_CETtraversal((ArrayList<Integer>) graph.getStartPoints());
    }

    private void T_CETtraversal(ArrayList<Integer> currentLevel) {
        ArrayList<Integer> nextLevel = new ArrayList<>();

        HashMap<Integer, Integer> nextLevelHash = new HashMap<>();

        for (Integer curNode : currentLevel) {
            if (graph.getStartPoints().contains(curNode)) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(curNode);
                paths[curNode].add(list);
            }

            for (int i = graph.rowIndex[curNode]; i < graph.rowIndex[curNode + 1]; i++) {
                int neighbour = graph.getColIndex()[i];
                for (ArrayList<Integer> path : paths[curNode]) {
                    ArrayList<Integer> list = new ArrayList<>(path);
                    list.add(neighbour);
                    paths[neighbour].add(list);
                }
                if (!nextLevelHash.containsKey(neighbour)) {
                    nextLevel.add(neighbour);
                    nextLevelHash.put(neighbour, 1);
                }
            }

            if (graph.getEndPoints().contains(curNode)) {
                for(ArrayList path: paths[curNode]){
                    if(saveToMem) validPaths.add(getPath(path));
                }
            }
            //paths[curNode.getIndex()] = null;
        }
        if (!nextLevel.isEmpty()) T_CETtraversal(nextLevel);
    }
}
