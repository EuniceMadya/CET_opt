package util;

import Components.Graph;
import Components.Vertex;

import java.util.*;

public class AnchorProcessor {

    public ArrayList<Integer> findAnchors(Graph graph, String type, int anchorNum) {
        System.out.println("  - Find anchor points for this graph...");

        if (type.equalsIgnoreCase("random")) return findRandomAnchors(graph, anchorNum);
        if (type.equalsIgnoreCase("degrees")) return findLargestDegreeAnchors(graph, anchorNum);

        System.out.println("WARNING: Anchor Type unknown!");
        return null;
    }

    public ArrayList<Integer> findRandomAnchors(Graph graph, int anchorNum) {
        ArrayList<Integer> anchorList = new ArrayList<>(graph.getStartPoints());
//        anchorList.addAll(graph.getEndPoints());
        Random random = new Random();
        while (anchorNum > 0) {
            int anchor = random.nextInt(graph.getNumVertex());
            if (anchorList.contains(anchor)) continue;
            anchorList.add(anchor);
            anchorNum--;
        }

        return anchorList;
    }

    public ArrayList<Integer> findLargestDegreeAnchors(Graph graph, int anchorNum) {
        ArrayList<Integer> anchorList = new ArrayList<>(graph.getStartPoints());
        HashMap<Integer, Integer> vertexDegrees = new HashMap<>();
        for (Vertex vertex : graph.getVertices()) {
            if (graph.getStartPoints().contains(vertex.getIndex())
                    || graph.getEndPoints().contains(vertex.getIndex()))
                continue;

            vertexDegrees.put(vertex.getIndex(), vertex.getNeighbours().size());
        }

        TreeMap<Integer,List<Integer>> degreeVertex = sortMap(vertexDegrees);

        for (Map.Entry<Integer, List<Integer>> entry : degreeVertex.descendingMap().entrySet()) {
            if (anchorNum <= 0) break;
            if (anchorNum - entry.getValue().size() >= 0) anchorList.addAll(entry.getValue());

            else {
                int remainder = entry.getValue().size() - anchorNum;
                while (remainder > 0) {
                    anchorList.add(entry.getValue().get((entry.getValue().size() - 1) / 2));
                    entry.getValue().remove((entry.getValue().size()-1)/2);
                    remainder--;
                }

            }
            anchorNum -= entry.getValue().size();
        }
//        anchorList.addAll(graph.getEndPoints());

        return anchorList;
    }

    /**
     * Source: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
     *
     * @param map to sort
     * @return map sorted by value
     */
    public TreeMap<Integer, List<Integer>> sortMap(HashMap<Integer, Integer> map) {

        TreeMap<Integer, List<Integer>> temp = new TreeMap<>();

        for (Map.Entry<Integer, Integer> element : map.entrySet()) {
            if (temp.get(element.getValue()) == null) temp.put(element.getValue(), new ArrayList<>());
            temp.get(element.getValue()).add(element.getKey());
        }
        return temp;
    }
}
