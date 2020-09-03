package util;

import Components.Graph;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GraphGenerator {


    public Graph generateGraphFromGrid(boolean[][] grid, Timestamp[] time) {
        Graph graph = new Graph();
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


    public Graph generateGraphFromPairs(ArrayList<int[]> pairs, Timestamp[] time, int jobCount) {
        Graph graph = new Graph();
        for (int i = 0; i < jobCount; i++) graph.addVertex(i);

        for (int i = 0; i < pairs.size(); i++) {
            int id = pairs.get(i)[0];
            int neighbour = pairs.get(i)[1];
            if (graph.getVertex(pairs.get(i)[0]) == null) graph.addVertex(id);
            graph.addEdge(id, neighbour);
        }
        return graph;
    }


    public Graph generateGraphFromLists(ArrayList<Integer>[] lists) {
        Graph graph = new Graph();
        for (int i = 0; i < lists.length; i++) {
            graph.addVertex(i);
            for (Integer j: lists[i]) {
                graph.addEdge(i, j);
            }
        }
        return graph;
    }

    public Graph buildGraph(boolean[][] dag) {
        return buildGraph(dag, null);
    }

    // legacy method, backup for when timestamps are needed
    public Graph buildGraph(boolean[][] dag, Timestamp[] timestamps) {
        GraphProcessor graphProcessor = new GraphProcessor();
        graphProcessor.preprocess(dag);
        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();
        Graph graph;

        graph = generateGraphFromGrid(dag, timestamps);

        //Find all start and end points
        graph.setStartPoints(starts);
        graph.setEndPoints(ends);

        return graph;
    }

    public Graph buildGraph(ArrayList<int[]> dag, int jobCount) {
        GraphProcessor graphProcessor = new GraphProcessor();
        graphProcessor.numVertices = jobCount;
        Graph graph;
        graph = generateGraphFromPairs(dag, null, jobCount);

        graphProcessor.preprocess(dag);
        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();

        graph.setStartPoints(starts);
        graph.setEndPoints(ends);


        return graph;
    }

    public Graph buildGraph(ArrayList<Integer>[] dag) {
        GraphProcessor graphProcessor = new GraphProcessor();
        graphProcessor.preprocess(dag);
        Graph graph;
        graph = generateGraphFromLists(dag);

        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();

        graph.setStartPoints(starts);
        graph.setEndPoints(ends);

        return graph;

    }


}
