package SimpleExperiment;

import java.util.ArrayList;

public class DoubleLeveling extends Concatenate {
    private int[] firstHalf ;
    private int[] secondHalf;

    DoubleLeveling(ArrayList<int[]> elements) {
        super(elements);
        firstConcatenate();
    }

    private void firstConcatenate() {

        firstHalf = new int[elements.get(0).length];
        secondHalf = new int[elements.get(0).length];

        System.arraycopy(elements.get(0), 0, firstHalf,  0, elements.get(0).length);
        for (int i = 0; i < elements.size()/2; i ++) {
            int [] element = elements.get(i);
            int[] inter = new int[element.length + firstHalf.length];
            System.arraycopy(firstHalf, 0, inter, 0, firstHalf.length);
            System.arraycopy(element, 0, inter, firstHalf.length, element.length);
            firstHalf = inter;
        }
        for (int i = elements.size()/2; i < elements.size(); i ++) {
            int [] element = elements.get(i);
            int[] inter = new int[element.length + secondHalf.length];
            System.arraycopy(secondHalf, 0, inter, 0, secondHalf.length);
            System.arraycopy(element, 0, inter, secondHalf.length, element.length);
            secondHalf = inter;
        }

    }

    @Override
    public int[] concatenate() {

        int[] result = new int[firstHalf.length + secondHalf.length];
        System.arraycopy(firstHalf, 0, result, 0, firstHalf.length);
        System.arraycopy(secondHalf, 0, result, firstHalf.length, secondHalf.length);

        return result;
    }


}
