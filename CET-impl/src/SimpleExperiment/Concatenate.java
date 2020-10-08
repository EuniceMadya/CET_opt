package SimpleExperiment;


import java.util.ArrayList;

public abstract class Concatenate {


    ArrayList<int[]> elements;

    ArrayList<int[]> results;

    public Concatenate(ArrayList<int[]> elements) {
        this.elements = elements;
        results = new ArrayList<>();
    }

    public abstract int[] concatenate();

    public void run() {
        for (int i = 0; i < 100000; i++) {
            results.add(concatenate());
        }
    }
}
