package SimpleExperiment;

import java.util.ArrayList;
import java.util.Scanner;

public class RunExperiment {

    public static void main(String[] args) {

        ArrayList<int[]> elements = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            elements.add(new int[10]);
            for (int j = 0; j < 10; j++)
                elements.get(i)[j] = i * 10 + j;
        }


        System.out.println("Which one to run?(Single/Double)");
        Concatenate concatenate;
        if(new Scanner(System.in).nextLine().equals("Single")) concatenate = new SingleLeveling(elements);
        else concatenate = new DoubleLeveling(elements);

        long startTime = System.nanoTime();
        concatenate.run();
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);

    }
}
