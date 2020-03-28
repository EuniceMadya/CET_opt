package util;

import Traversal.Graph;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class GraphGenerator {


    public Graph generateGraph(boolean grid[][], Timestamp time[]){
        Graph graph = new Graph();
        int size = grid.length;

        for(int i = 0; i < size; i ++){
            graph.addVertex(i, time[i]);
            List <Integer> edges = new ArrayList<>();
            for(int j = 0; i < size; j ++){
                if(grid[i][j]) edges.add(j);
            }
        }

        return graph;
    }

}
