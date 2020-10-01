package SimpleExperiment;

import java.util.ArrayList;

public class SingleLeveling extends Concatenate {
    SingleLeveling(ArrayList<int[]> elements) {
        super(elements);
    }

    @Override
    public int[] concatenate() {
        int[] result = new int[1000];
        for (int i = 0; i < elements.size(); i++) {
            System.arraycopy(elements.get(i), 0, result, i * 10, elements.get(i).length);
        }
        return result;
    }
}
