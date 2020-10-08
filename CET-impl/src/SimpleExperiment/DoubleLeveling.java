package SimpleExperiment;

import java.util.ArrayList;

public class DoubleLeveling extends Concatenate {
    int[] firstHalf = new int[500000];
    int[] secondHalf = new int[500000];

    DoubleLeveling(ArrayList<int[]> elements) {
        super(elements);
        firstConcatenate();
    }

    public void firstConcatenate() {
        for (int i = 0; i < 50; i++) {
            System.arraycopy(elements.get(i), 0, firstHalf, i * 10000, elements.get(i).length);
        }

        for (int i = 0; i < 50; i++) {
            System.arraycopy(elements.get(i), 0, secondHalf, i * 10000, elements.get(i + 50).length);
        }
    }

    @Override
    public int[] concatenate() {

        int[] result = new int[1000000];
        System.arraycopy(firstHalf, 0, result, 0, 500000);
        System.arraycopy(secondHalf, 0, result, 500000, 500000);


        return result;
    }


}
