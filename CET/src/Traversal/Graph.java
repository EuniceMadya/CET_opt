package Traversal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, Vertex> vertices;
    private Map<Integer, List<Integer>> graph;
    int numVertex;

    public Graph(){
        vertices = new HashMap<>();
        graph = new HashMap<>();
        numVertex = 0;
    }


    public Vertex addVertex(int index, Timestamp time){

        Vertex newVertex = new Vertex(index, time);
        vertices.put(index, newVertex);
        graph.put(index, new ArrayList<>());
        numVertex ++;
        return newVertex;
    }

    public void addEdges(int index, List<Integer> edges){
        Vertex vertex = vertices.get(index);
        if(vertex == null) {
            System.out.println("no existing vertex to the corresponding index");
            return;
        }
        graph.replace(index, edges);
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

    public Vertex getVertex(int i){
        return vertices.get(i);
    }

    public List<Integer> getEdges(int i){
        return graph.get(i);
    }


    public class Vertex{
        int index;
        Timestamp time;
        Vertex(int index, Timestamp time){
            this.index = index;
            this.time = time;
        }
    }

    public int getNumVertex(){
        return numVertex;
    }

}
