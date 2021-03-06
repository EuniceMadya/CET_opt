package SimpleExperiment;

import java.util.ArrayList;

public class SingleLeveling extends Concatenate {
    SingleLeveling(ArrayList<int[]> elements) {
        super(elements);
    }

    @Override
    public int[] concatenate() {
        int []result = new int[elements.get(0).length];
        System.arraycopy(elements.get(0), 0, result,  0, elements.get(0).length);
        for (int[] element : elements) {
            int[] inter = new int[element.length + result.length];
            System.arraycopy(result, 0, inter, 0, result.length);
            System.arraycopy(element, 0, inter, result.length, element.length);
            result = inter;
        }
        return result;
    }
}
