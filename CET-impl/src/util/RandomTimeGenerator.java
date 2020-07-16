package util;

import java.sql.Timestamp;
import java.util.*;

public class RandomTimeGenerator {
    public Timestamp generateTime(long time) {
        Random generator = new Random(time);
        long mod = 84664510000L + generator.nextLong() % 10000L;
        long addOn = generator.nextLong() % mod;
        return new Timestamp(time + addOn);
    }

    public Timestamp getInitialTimestamp() {
        String str = "2000-01-01 00:00:00";
        Timestamp timestamp = Timestamp.valueOf(str);
        return timestamp;
    }


    /**
     * Generate time sequence based on the vertices order
     *
     * @param grid:        adjacency matrix
     * @param startPoints: all the start points in the graph
     * @return array of timestamps
     */
    public Timestamp[] generateTimes(boolean[][] grid, List<Integer> startPoints) {
        Timestamp[] timestamps = new Timestamp[grid.length];

        for (int i = 0; i < timestamps.length; i++) {
            timestamps[i] = new Timestamp(Long.MAX_VALUE);
        }

        Queue<Integer> priorityQueue = new PriorityQueue<>();

        for (int index = 0; index < grid.length; index++) {

            if (startPoints.contains(index)) {
                timestamps[index] = generateTime(getInitialTimestamp().getTime());
                priorityQueue.add(index);
            }
        }

        while (!priorityQueue.isEmpty()) {
            int index = priorityQueue.poll();
            for (int i = 0; i < grid.length; i++) {
                if (grid[index][i]) {
                    timestamps[i] = generateTime(timestamps[index].getTime());
                    priorityQueue.add(i);
                }
            }
        }

        System.out.println("timestamps: " + Arrays.toString(timestamps));


        return timestamps;
    }
}
