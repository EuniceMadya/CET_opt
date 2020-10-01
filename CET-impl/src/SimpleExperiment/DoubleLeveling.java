package SimpleExperiment;

import java.util.ArrayList;

public class DoubleLeveling extends Concatenate {


    DoubleLeveling(ArrayList<int[]> elements) {
        super(elements);
    }

    @Override
    public int[] concatenate() {
        int[] firstHalf = new int[500];
        int[] secondHalf = new int[500];
        int[] result = new int[1000];
        for (int i = 0; i < 50; i++) {
            System.arraycopy(elements.get(i), 0, firstHalf, i * 10, elements.get(i).length);
        }

        for (int i = 0; i < 50; i++) {
            System.arraycopy(elements.get(i), 0, secondHalf, i * 10, elements.get(i + 50).length);
        }

        System.arraycopy(firstHalf, 0, result, 0, 500);
        System.arraycopy(secondHalf, 0, result, 500, 500);
        firstHalf = null;
        secondHalf = null;

        return result;
    }

}
