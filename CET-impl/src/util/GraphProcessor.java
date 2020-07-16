package util;

import java.util.ArrayList;
import java.util.List;

public class GraphProcessor {
    private List<Integer> starts;
    private List<Integer> ends;
    private boolean[][] grid;

    // find graph start and end points
    public GraphProcessor(boolean[][] grid) {
        starts = new ArrayList<>();
        ends = new ArrayList<>();
        this.grid = grid;
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
            preprocessGrid();
        }
        return starts;

    }

    public List<Integer> findEnds() {
        //lazy load
        if (ends.size() == 0) {
            preprocessGrid();
        }
        return ends;

    }

}
