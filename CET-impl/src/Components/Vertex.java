package Components;

import java.sql.Timestamp;

public class Vertex {
    int index;
    Timestamp time;

    public Vertex(int index) {
        this(index, null);
    }

    public Vertex(int index, Timestamp time) {
        this.index = index;
        this.time = time;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
