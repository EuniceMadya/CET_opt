package Components;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, Vertex> vertices;
    protected int numVertex;

    private List<Vertex> startVertices;
    private List<Vertex> endVertices;


    private List<Integer> startPoints;
    private List<Integer> endPoints;

    public Graph() {
        vertices = new HashMap<>(numVertex);
        numVertex = 0;
        startPoints = new ArrayList<>();
        endPoints = new ArrayList<>();
        startVertices = new ArrayList<>();
        endVertices = new ArrayList<>();
    }

    public boolean addStart(int index) {
        if (vertices.get(index) != null) {
            startPoints.add(index);
        }
        return false;
    }

    public boolean addEnd(int index) {
        if (vertices.get(index) != null) {
            endPoints.add(index);
        }
        return false;
    }


    public Vertex addVertex(int index, Timestamp time) {
        Vertex newVertex = new Vertex(index, time);
        vertices.put(index, newVertex);
        numVertex++;
        return newVertex;
    }

    public Vertex addVertex(int index) {
        Vertex newVertex = new Vertex(index);
        vertices.put(index, newVertex);
        numVertex++;
        return newVertex;
    }

    public void addEdges(int index, List<Integer> edges) {
        Vertex vertex = vertices.get(index);
        if (vertex == null) {
            System.out.println("no existing vertex to the corresponding index");
            return;
        }
        vertices.get(index).addNeighbours(edges);
    }

    public void addEdge(int index, int neighbour) {
        Vertex vertex = vertices.get(index);
        if (vertex == null) {
            System.out.println("no existing vertex to the corresponding index");
            return;
        }
        vertices.get(index).addNeighbour(neighbour);
    }

    public void removeVertex(Integer index) {
        Vertex vertex = vertices.get(index);
        if (vertex == null) {
            System.out.println("no existing vertex to the corresponding index");
            return;
        }
        vertices.remove(index);
        for (Vertex v : vertices.values()) {
            v.removeNeighbour(index);
        }
        System.out.println("Vertex removed");

    }

    public Vertex getVertex(int i) {
        return vertices.get(i);
    }

    public List<Integer> getStartPoints() {
        return startPoints;
    }

    public List<Vertex> getStartVertices() {
        if (startVertices.size() == 0) {
            for (Integer start : startPoints) startVertices.add(vertices.get(start));
        }
        return startVertices;
    }


    public List<Integer> getEndPoints() {
        return endPoints;
    }

    public List<Vertex> getEndVertices() {
        if (endVertices.size() == 0) {
            for (Integer end : endPoints) startVertices.add(vertices.get(end));
        }
        return endVertices;
    }

    public void setStartPoints(List<Integer> startPoints) {
        this.startPoints = startPoints;
    }

    public void setEndPoints(List<Integer> endPoints) {
        this.endPoints = endPoints;
    }

    public List<Integer> getEdges(int i) {
        return vertices.get(i).getNeighbours();
    }


    public int getNumVertex() {
        return numVertex;
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices.values());
    }

}
