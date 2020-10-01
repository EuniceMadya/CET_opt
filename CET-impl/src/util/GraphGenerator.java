package util;

import Components.CompressedGraph;

import java.util.ArrayList;

class GraphGenerator {


    CompressedGraph buildGraph(boolean[][] dag) {
        int edgeNum = 0;
        for (boolean[] col : dag)
            for (boolean b : col)
                if (b) edgeNum++;

        CompressedGraph dagGraph = new CompressedGraph(edgeNum, dag.length + 1);
        int[] colIndex = dagGraph.getColIndex();
        int[] rowIndex = dagGraph.getRowIndex();
        int colCounter = 0;
        int rowCounter = 0;
        rowIndex[rowCounter++] = 0;
        for (boolean[] booleans : dag) {
            for (int j = 0; j < dag.length; j++) {
                if (booleans[j]) {
                    colIndex[colCounter++] = j;
                }
            }
            rowIndex[rowCounter++] = colCounter;
        }

        return dagGraph;
    }


    CompressedGraph buildGraph(ArrayList<int[]> pairs, int jobCount) {
        CompressedGraph graph = new CompressedGraph(pairs.size(), jobCount + 1);
        int colCounter = 0;
        int rowCounter = 0;

        graph.getColIndex()[colCounter++] = pairs.get(0)[1];
        graph.getRowIndex()[rowCounter++] = colCounter;
        if (pairs.get(0)[0] != 0) graph.getRowIndex()[rowCounter++] = colCounter;

        for (int i = 1; i < pairs.size(); i++) {
            int source = pairs.get(i)[0];
            int prevSource = pairs.get(i - 1)[0];
            int dest = pairs.get(i)[1];

            graph.getColIndex()[colCounter++] = dest;
            if (source > prevSource)
                for (int j = 0; j < source - prevSource; j++)
                    graph.getRowIndex()[rowCounter++] = colCounter - 1;
        }
        while (rowCounter < jobCount + 1) {
            graph.getRowIndex()[rowCounter++] = colCounter;
        }
        return graph;
    }

    CompressedGraph buildGraph(ArrayList<Integer>[] dag) {
        int edgeNum = 0;

        for (ArrayList<Integer> list : dag)
            edgeNum += list.size();
        CompressedGraph graph = new CompressedGraph(edgeNum, dag.length + 1);

        int rowCount = 0;
        int colCount = 0;
        graph.getRowIndex()[rowCount++] = 0;


        for (ArrayList<Integer> list : dag) {
            graph.getRowIndex()[rowCount++] = colCount + list.size();
            for (int i : list)
                graph.getColIndex()[colCount++] = i;
        }

        return graph;

    }


}
