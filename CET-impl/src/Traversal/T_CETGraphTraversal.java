package Traversal;

import Components.Graph;
import Components.Vertex;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class T_CETGraphTraversal extends GraphTraversal {

    public ArrayList<ArrayList<Integer>>[] paths;

    public T_CETGraphTraversal(Graph graph, Timestamp windowSize) {
        super(graph, windowSize);
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
        T_CETtraversal((ArrayList<Vertex>) graph.getStartVertices());
    }

    private void T_CETtraversal(ArrayList<Vertex> currentLevel) {
        ArrayList<Vertex> nextLevel = new ArrayList<>();

        HashMap<Integer, Integer> nextLevelHash = new HashMap<>();

        for (Vertex curNode : currentLevel) {
            if (graph.getStartPoints().contains(curNode.getIndex())){
                ArrayList<Integer> list = new ArrayList<>();
                list.add(curNode.getIndex());
                paths[curNode.getIndex()].add(list);
            }

            for (Integer neighbour : curNode.getNeighbours()) {
                for (ArrayList<Integer> path : paths[curNode.getIndex()]) {
                    ArrayList<Integer> list = new ArrayList<>(path);
                    list.add(neighbour);
                    paths[neighbour].add(list);
                }
                if (!nextLevelHash.containsKey(neighbour)) {
                    nextLevel.add(graph.getVertex(neighbour));
                    nextLevelHash.put(neighbour, 1);
                }
            }

            if (graph.getEndPoints().contains(curNode.getIndex())) {
                validPaths.addAll(paths[curNode.getIndex()]);
            }
            //paths[curNode.getIndex()] = null;
        }
        if (!nextLevel.isEmpty()) T_CETtraversal(nextLevel);
    }
}
