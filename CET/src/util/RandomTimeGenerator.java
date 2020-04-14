package util;

import java.sql.Timestamp;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class RandomTimeGenerator {
    public Timestamp generateTime(long time) {
        Random generator = new Random(time);
        long newTime = generator.nextLong();

        return new Timestamp(newTime);
    }

    public Timestamp[] generateTimes(boolean[][] grid, List<Integer> startPoints) {
        Timestamp[] timestamps = new Timestamp[grid.length];

        for (int i = 0; i < timestamps.length; i++) {
            timestamps[i] = new Timestamp(Long.MAX_VALUE);
        }

        Queue<Integer> priorityQueue = new PriorityQueue<>();

        for (int index = 0; index < grid.length; index++) {

            if (startPoints.contains(index)) {
                timestamps[index] = generateTime(1000000);
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

        return timestamps;
    }
}
