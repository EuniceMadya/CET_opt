package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphProcessor {
    private List<Integer> starts;
    private List<Integer> ends;
    private boolean[][] grid;
    private ArrayList<int[]> matrix;
    int numVertices;

    // find graph start and end points
    public GraphProcessor(boolean[][] grid) {
        starts = new ArrayList<>();
        ends = new ArrayList<>();
        this.grid = grid;
    }

    public GraphProcessor(ArrayList<int[]> matrix, int jobCount) {
        starts = new ArrayList<>();
        ends = new ArrayList<>();
        this.matrix = matrix;
        numVertices = jobCount;
    }

    public void preprocess() {
        if (grid != null) preprocessGrid();
        if (matrix != null) preprocessMatrix();
    }

    public void preprocessMatrix() {
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


    public void preprocessGrid() {
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


    public List<Integer> findStarts() {
        //lazy load
        if (starts.size() == 0) {
            preprocess();
        }
        return starts;

    }

    public List<Integer> findEnds() {
        //lazy load
        if (ends.size() == 0) {
            preprocess();
        }
        return ends;

    }

}
