package Traversal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, Vertex> vertices;
    private Map<Vertex, List<Integer>> graph;


    public Vertex addVertex(int index, Timestamp time){

        Vertex newVertex = new Vertex(index, time);
        vertices.put(index, newVertex);
        graph.put(newVertex, new ArrayList<>());
        return newVertex;
    }



    public void addEdges(int index, List<Integer> edges){
        Vertex vertex = vertices.get(index);
        if(vertex == null) {
            System.out.println("no existing vertex to the corresponding index");
            return;
        }
        graph.replace(vertex, edges);
    }

    public void removeVertex(int index){
        Vertex vertex = vertices.get(index);
        if(vertex == null) {
            System.out.println("no existing vertex to the corresponding index");
            return;
        }
        graph.remove(vertex);
        vertices.remove(index);
        System.out.println("Vertex removed");

    }


    public class Vertex{
        int index;
        Timestamp time;
        Vertex(int index, Timestamp time){
            this.index = index;
            this.time = time;
        }


    }

}
