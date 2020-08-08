package Components;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Vertex {
    int index;
    Timestamp time;
    List<Integer> edges;


    public Vertex(int index) {
        this(index, null);
    }

    public Vertex(int index, Timestamp time) {
        this.index = index;
        this.time = time;
        edges = new ArrayList<>();

    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void addNeighbour(Integer neighbour) {
        edges.add(neighbour);
    }

    public void addNeighbours(List<Integer> neighbour) {
        edges.addAll(neighbour);
    }

    public void removeNeighbour(Integer neighbour) {
        edges.remove(neighbour);
    }

    public List<Integer> getNeighbours() {
        return edges;
    }
}
