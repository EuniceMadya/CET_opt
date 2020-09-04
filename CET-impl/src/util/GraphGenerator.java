package util;

import Components.GeneralGraph;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GraphGenerator {


    public GeneralGraph generateGraphFromGrid(boolean[][] grid, Timestamp[] time) {
        GeneralGraph graph = new GeneralGraph();
        int size = grid.length;

        for (int i = 0; i < size; i++) {

            if (time == null) graph.addVertex(i);
            else graph.addVertex(i, time[i]);

            List<Integer> edges = new ArrayList<>();
            for (int j = 0; j < size; j++)
                if (grid[i][j]) edges.add(j);

            graph.addEdges(i, edges);
        }

        return graph;
    }


    public GeneralGraph generateGraphFromPairs(ArrayList<int[]> pairs, Timestamp[] time, int jobCount) {
        GeneralGraph graph = new GeneralGraph();
        for (int i = 0; i < jobCount; i++) graph.addVertex(i);

        for (int i = 0; i < pairs.size(); i++) {
            int id = pairs.get(i)[0];
            int neighbour = pairs.get(i)[1];
            if (graph.getVertex(pairs.get(i)[0]) == null) graph.addVertex(id);
            graph.addEdge(id, neighbour);
        }
        return graph;
    }


    public GeneralGraph generateGraphFromLists(ArrayList<Integer>[] lists) {
        GeneralGraph graph = new GeneralGraph();
        for (int i = 0; i < lists.length; i++) {
            graph.addVertex(i);
            for (Integer j : lists[i]) {
                graph.addEdge(i, j);
            }

        }
        return graph;
    }

    public GeneralGraph buildGraph(boolean[][] dag) {
        return buildGraph(dag, null);
    }

    // legacy method, backup for when timestamps are needed
    public GeneralGraph buildGraph(boolean[][] dag, Timestamp[] timestamps) {
        GraphProcessor graphProcessor = new GraphProcessor();
        graphProcessor.preprocess(dag);
        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();
        GeneralGraph graph;

        graph = generateGraphFromGrid(dag, timestamps);

        //Find all start and end points
        graph.setStartPoints(starts);
        graph.setEndPoints(ends);

        return graph;
    }

    public GeneralGraph buildGraph(ArrayList<int[]> dag, int jobCount) {
        GraphProcessor graphProcessor = new GraphProcessor();
        GeneralGraph graph;
        graph = generateGraphFromPairs(dag, null, jobCount);

        graphProcessor.preprocess(dag, jobCount);
        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();

        graph.setStartPoints(starts);
        graph.setEndPoints(ends);


        return graph;
    }

    public GeneralGraph buildGraph(ArrayList<Integer>[] dag) {
        GraphProcessor graphProcessor = new GraphProcessor();
        graphProcessor.preprocess(dag);
        GeneralGraph graph;
        graph = generateGraphFromLists(dag);

        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();

        graph.setStartPoints(starts);
        graph.setEndPoints(ends);

        return graph;

    }


}
