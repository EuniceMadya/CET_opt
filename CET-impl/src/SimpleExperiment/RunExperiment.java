package SimpleExperiment;

import java.util.ArrayList;
import java.util.Scanner;

public class RunExperiment {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("numElements"); // 100

        int numElements = Integer.parseInt(scanner.nextLine());
        System.out.println("individualSize"); // 50

        int individualSize = Integer.parseInt(scanner.nextLine());

        ArrayList<int[]> elements = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            elements.add(new int[individualSize]);
            for (int j = 0; j < individualSize; j++)
                elements.get(i)[j] = i * individualSize + j;
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
