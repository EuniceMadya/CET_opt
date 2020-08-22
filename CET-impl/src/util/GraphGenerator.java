package util;

import Components.Graph;
import util.dagGen.DAGTools;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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


    public Graph generateGraphFromMatrix(ArrayList<int[]> matrix, Timestamp[] time, int jobCount) {
        Graph graph = new Graph();
        for (int i = 0; i < jobCount; i++) graph.addVertex(i);

        for (int i = 0; i < matrix.size(); i++) {
            int id = matrix.get(i)[0];
            int neighbour = matrix.get(i)[1];
            if (graph.getVertex(matrix.get(i)[0]) == null) graph.addVertex(id);
            graph.addEdge(id, neighbour);
        }
        return graph;
    }

    public Graph buildGraph(boolean[][] dag) {
        return buildGraph(dag, null);
    }

    // legacy method, backup for when timestamps are needed
    public Graph buildGraph(boolean[][] dag, Timestamp[] timestamps) {
        GraphProcessor graphProcessor = new GraphProcessor(dag);
        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();
        Graph graph;

        graph = generateGraphFromGrid(dag, timestamps);

        //Find all start and end points
        graph.setStartPoints(starts);
        graph.setEndPoints(ends);


        System.out.println("-- start points: " + Arrays.toString(starts.toArray()));
        System.out.println("-- end points: " + Arrays.toString(ends.toArray()));

//        System.out.println(DAGTools.printDAG(dag));
        System.out.println("\n- Generated a " + dag.length + "x" + dag[0].length +
                " DAG with " + DAGTools.getEdges(dag) + " edges.#");
        System.out.println("--------------------------------------------------------------------------------------\n");

        return graph;
    }

    public Graph buildGraph(ArrayList<int[]> dag, int jobCount) {
        GraphProcessor graphProcessor = new GraphProcessor(dag, jobCount);
        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();
        Graph graph;
        graph = generateGraphFromMatrix(dag, null, jobCount);
        graph.setStartPoints(starts);
        graph.setEndPoints(ends);
        System.out.println("start points: " + Arrays.toString(starts.toArray()));
        System.out.println("end points: " + Arrays.toString(ends.toArray()));

        return graph;
    }

}
