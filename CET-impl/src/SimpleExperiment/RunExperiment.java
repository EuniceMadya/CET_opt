package SimpleExperiment;

import java.util.ArrayList;
import java.util.Scanner;

public class RunExperiment {

    public static void main(String[] args) {

        ArrayList<int[]> elements = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            elements.add(new int[100]);
            for (int j = 0; j < 100; j++)
                elements.get(i)[j] = i * 10000 + j;
        }


        System.out.println("Which one to run?(Single/Double)");
        Concatenate concatenate;

        if(new Scanner(System.in).nextLine().equals("Single")) concatenate = new SingleLeveling(elements);
        else concatenate = new DoubleLeveling(elements);

        long startTime = System.nanoTime();
        concatenate.run();
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/ Math.pow(10,9));

    }
}
