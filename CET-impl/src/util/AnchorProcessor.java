package util;

import Components.CompressedGraph;

import java.util.*;
import java.util.stream.IntStream;

public class AnchorProcessor {

    private CompressedGraph graph;

    public AnchorProcessor(CompressedGraph graph){
        this.graph = graph;
    }

    public int[] findAnchors(String type, int anchorNum) {
        System.out.println("  - Find anchor points for this graph...");

        if (type.equalsIgnoreCase("random")) return findRandomAnchors(anchorNum);
        if (type.equalsIgnoreCase("largest")) return findLargestDegreeAnchors( anchorNum);
        if (type.equalsIgnoreCase("distro")) return findEquallyDistributedAnchors(anchorNum);

        System.out.println("WARNING: Anchor Type unknown!");
        return null;
    }

    private int[] findRandomAnchors(int anchorNum) {
        int[] anchorList = new int[graph.getStartPointNum() + anchorNum];

        for(int i = 0; i < graph.getStartPointNum(); i ++) anchorList[i] = graph.getStartPoints().get(i);
//        anchorList.addAll(graph.getEndPoints());
        Random random = new Random();
        int counter = 0;
        while (counter < anchorNum) {
            int anchor = random.nextInt(graph.getNumVertex());

            if (IntStream.of(anchorList).anyMatch(x -> x == anchor)) continue;

            anchorList[graph.getStartPointNum() + counter++] = anchor;
        }

        return anchorList;
    }

    private int[] findLargestDegreeAnchors(int anchorNum) {
        int[] anchorList = new int[graph.getStartPointNum() + anchorNum];

        for(int i = 0; i < graph.getStartPointNum(); i ++) anchorList[i] = graph.getStartPoints().get(i);

        HashMap<Integer, Integer> vertexDegrees = new HashMap<>();
        for (int i = 0; i < graph.getNumVertex(); i ++) {
            if (graph.startContains(i)
                    || graph.endContains(i))
                continue;
            //put degree with node num
            vertexDegrees.put(i, graph.getNumDegree(i));
        }

        TreeMap<Integer, List<Integer>> degreeVertex = sortMap(vertexDegrees);

        int start = graph.getStartPointNum();

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

    private int[] findEquallyDistributedAnchors(int anchorNum){
        Stack <Integer> topStack = new Stack<>();
        boolean [] visited = new boolean[graph.getNumVertex()];
        Arrays.fill(visited, false);

        for (int i: graph.getStartPoints()){
            if (!visited[i])
                topologicalSort(i, visited, topStack);
        }
        ArrayList<Integer> results = new ArrayList<>(graph.getStartPoints());

        // Customized topological order of the graph
        // with all start points at the front
        while(!topStack.empty()){
            int r = topStack.pop();
            if(!results.contains(r))
                results.add(r);
        }


        int[] anchorList = new int [graph.getStartPointNum() + anchorNum];

        for(int i = 0; i < graph.getStartPointNum(); i ++) anchorList[i] = graph.getStartPoints().get(i);

        // limit the number of anchor if it's too many
        if((graph.getNumVertex() - graph.getStartPointNum() + 1 ) / (anchorNum + 1) < 2) {
            System.out.println("Anchor num too large! Reducing to "+ (graph.getNumVertex() - graph.getStartPointNum() + 1 )/2);
            anchorNum = (graph.getNumVertex() - graph.getStartPointNum() + 1 )/2 - 1;
        }

        int spacing = (graph.getNumVertex() - graph.getStartPointNum()) / anchorNum;

        for(int i = 0; i < anchorNum; i ++){
            anchorList[i + graph.getStartPointNum()] = results.get((i + 1) *spacing);
        }

        return anchorList;
    }

    // reference from https://www.geeksforgeeks.org/topological-sorting/
    private void topologicalSort(int s, boolean [] visited, Stack<Integer> stack){

        visited[s] = true;

        for (int i = graph.rowIndex[s]; i < graph.rowIndex[s+ 1]; i++) {
            int neighbour = graph.colIndex[i];
            if (!visited[neighbour])
                topologicalSort(neighbour, visited, stack);
        }

        stack.push(s);

    }


}
