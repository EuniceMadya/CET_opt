package util;

import Components.CompressedGraph;

import java.util.*;
import java.util.stream.IntStream;

public class AnchorProcessor {

    public int[] findAnchors(CompressedGraph graph, String type, int anchorNum) {
        System.out.println("  - Find anchor points for this graph...");

        if (type.equalsIgnoreCase("random")) return findRandomAnchors(graph, anchorNum);
        if (type.equalsIgnoreCase("largest")) return findLargestDegreeAnchors(graph, anchorNum);

        System.out.println("WARNING: Anchor Type unknown!");
        return null;
    }

    private int[] findRandomAnchors(CompressedGraph graph, int anchorNum) {
        int[] anchorList = new int[graph.getStartPoints().size() + anchorNum];

        for(int i = 0; i < graph.getStartPoints().size(); i ++) anchorList[i] = graph.getStartPoints().get(i);
//        anchorList.addAll(graph.getEndPoints());
        Random random = new Random();
        int counter = 0;
        while (counter < anchorNum) {
            int anchor = random.nextInt(graph.getNumVertex());

            if (IntStream.of(anchorList).anyMatch(x -> x == anchor)) continue;

            anchorList[graph.getStartPoints().size() + counter++] = anchor;
        }

        return anchorList;
    }

    private int[] findLargestDegreeAnchors(CompressedGraph graph, int anchorNum) {
        int[] anchorList = new int[graph.getStartPoints().size() + anchorNum];

        for(int i = 0; i < graph.getStartPoints().size(); i ++) anchorList[i] = graph.getStartPoints().get(i);

        HashMap<Integer, Integer> vertexDegrees = new HashMap<>();
        for (int i = 0; i < graph.getNumVertex(); i ++) {
            if (graph.getStartPoints().contains(i)
                    || graph.getEndPoints().contains(i))
                continue;
            //put degree with node num
            vertexDegrees.put(i, graph.getNumDegree(i));
        }

        TreeMap<Integer, List<Integer>> degreeVertex = sortMap(vertexDegrees);

        int start = graph.getStartPoints().size();

        for (Map.Entry<Integer, List<Integer>> entry : degreeVertex.descendingMap().entrySet()) {
            if (anchorNum <= 0) break;

            for(int i : entry.getValue())
                if(start < anchorList.length)
                    anchorList[start ++] = i;

            anchorNum -= entry.getValue().size();
        }

        return anchorList;
    }

    /**
     * Source: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
     *
     * @param map to sort
     * @return map sorted by value
     */
    private TreeMap<Integer, List<Integer>> sortMap(HashMap<Integer, Integer> map) {

        TreeMap<Integer, List<Integer>> temp = new TreeMap<>();

        for (Map.Entry<Integer, Integer> element : map.entrySet()) {
            temp.computeIfAbsent(element.getValue(), k -> new ArrayList<>());
            temp.get(element.getValue()).add(element.getKey());
        }
        return temp;
    }
}
