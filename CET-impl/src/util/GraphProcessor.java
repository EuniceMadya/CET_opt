package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphProcessor {
    private List<Integer> starts;
    private List<Integer> ends;

    public GraphProcessor() {
        starts = new ArrayList<>();
        ends = new ArrayList<>();
    }

    public void preprocess(ArrayList<int[]> matrix, int numVertices) {
        boolean[] isStart = new boolean[numVertices];
        boolean[] isEnd = new boolean[numVertices];
        Arrays.fill(isStart, true);
        Arrays.fill(isEnd, true);

        for (int[] pair : matrix) {
            isEnd[pair[0]] = false;
            isStart[pair[1]] = false;
        }

        for (int i = 0; i < numVertices; i++) {
            if (isStart[i]) starts.add(i);
            if (isEnd[i]) ends.add(i);
        }
    }


    public void preprocess(boolean[][] grid) {
        for (int index = 0; index < grid.length; index++) {
            boolean isStart = true;
            boolean isEnd = true;
            for (int col = 0; col < grid.length; col++) {
                if (grid[col][index]) {
                    isStart = false;
                    break;
                }
            }
            for (int j = 0; j < grid.length; j++) {
                if (grid[index][j]) {
                    isEnd = false;
                    break;
                }
            }
            if (isStart) {
                starts.add(index);
            }
            if (isEnd) {
                ends.add(index);
            }
        }

    }

    public void preprocess(ArrayList<Integer>[] lists) {
        boolean[] isStart = new boolean[lists.length];
        boolean[] isEnd = new boolean[lists.length];
        Arrays.fill(isStart, true);
        Arrays.fill(isEnd, false);
        for (int i = 0; i < lists.length; i++) {

            if (lists[i].size() == 0) isEnd[i] = true;
            for (Integer j : lists[i]) isStart[j] = false;
        }
        for (int i = 0; i < lists.length; i++) {

            if (isStart[i]) starts.add(i);
            if (isEnd[i]) ends.add(i);
        }
    }

    public List<Integer> findStarts() {
        //lazy load

        return starts;

    }

    public List<Integer> findEnds() {
        //lazy load

        return ends;

    }

}
