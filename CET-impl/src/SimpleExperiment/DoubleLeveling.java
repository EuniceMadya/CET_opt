package SimpleExperiment;

import java.util.ArrayList;

public class DoubleLeveling extends Concatenate {
    int[] firstHalf = new int[5000];
    int[] secondHalf = new int[5000];

    DoubleLeveling(ArrayList<int[]> elements) {
        super(elements);
        firstConcatenate();
    }

    public void firstConcatenate() {
        for (int i = 0; i < 50; i++) {
            System.arraycopy(elements.get(i), 0, firstHalf, i * 100, elements.get(i).length);
        }

        for (int i = 0; i < 50; i++) {
            System.arraycopy(elements.get(i), 0, secondHalf, i * 100, elements.get(i + 50).length);
        }
    }

    @Override
    public int[] concatenate() {

        int[] result = new int[10000];
        System.arraycopy(firstHalf, 0, result, 0, 5000);
        System.arraycopy(secondHalf, 0, result, 5000, 5000);

        return result;
    }


}
