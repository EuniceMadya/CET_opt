package SimpleExperiment;

import java.util.ArrayList;
import java.util.Scanner;

public class RunExperiment {

    public static void main(String[] args) {

        ArrayList<int[]> elements = new ArrayList<>(10000);
        for (int i = 0; i < 100; i++) {
            elements.add(new int[100]);
            for (int j = 0; j < 100; j++)
                elements.get(i)[j] = i * 100 + j;
        }


        Concatenate concatenate;

        String type = System.getProperty("type", "Single");
        if (type == null) {
            System.out.println("Which one to run?(Single/Double)");

            type = new Scanner(System.in).nextLine();
        }
        System.out.println("Running: type " + type);

        long startTime = System.nanoTime();
        if (type.equals("Single")) concatenate = new SingleLeveling(elements);
        else concatenate = new DoubleLeveling(elements);

        concatenate.run();
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime) / Math.pow(10, 9));

    }
}
