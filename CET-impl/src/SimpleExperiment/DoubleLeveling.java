package SimpleExperiment;

import java.util.ArrayList;

public class DoubleLeveling extends Concatenate {
    private int[] firstHalf = new int[2500];
    private int[] secondHalf = new int[2500];

    DoubleLeveling(ArrayList<int[]> elements) {
        super(elements);
        firstConcatenate();
    }

    private void firstConcatenate() {
        for (int i = 0; i < 50; i++) {
            System.arraycopy(elements.get(i), 0, firstHalf, i * 100, elements.get(i).length);
        }

        for (int i = 0; i < 50; i++) {
            System.arraycopy(elements.get(i), 0, secondHalf, i * 100, elements.get(i + 50).length);
        }
    }

    @Override
    public int[] concatenate() {

        int[] result = new int[5000];
        System.arraycopy(firstHalf, 0, result, 0, firstHalf.length);
        System.arraycopy(secondHalf, 0, result, firstHalf.length, secondHalf.length);

        return result;
    }


}
