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

    public void run(int numRun) {
        for (int i = 0; i < numRun; i++) {
            results.add(concatenate());
        }
    }
}
