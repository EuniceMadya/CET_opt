package Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompressedGraph{
    int[] colIndex;
    int[] rowIndex;
    ArrayList<Integer> startPoints;
    ArrayList<Integer> endPoints;

    public CompressedGraph(int colNum, int rowNum) {
        colIndex = new int[colNum];
        rowIndex = new int[rowNum];
        startPoints = new ArrayList<>();
        endPoints = new ArrayList<>();

        Arrays.fill(colIndex, -1);
        Arrays.fill(rowIndex, -1);
    }

    public int[] getColIndex() {
        return colIndex;
    }

    public int[] getRowIndex() {
        return rowIndex;
    }

    public List<Integer> getStartPoints() {
        if(startPoints.size() == 0) loadStartPoints();
        return startPoints;
    }

    private void loadStartPoints(){
        boolean isStart [] = new boolean[rowIndex.length - 1];

        Arrays.fill(isStart, true);

        for(int i : colIndex)  isStart[i] = false;
        for(int i = 0; i < getNumVertex(); i ++)
            if(isStart[i]) startPoints.add(i);
    }

    public List<Integer> getEndPoints() {
        if(endPoints.size() == 0) loadEndPoints();

        return endPoints;
    }

    private void loadEndPoints(){
        boolean isEnd [] = new boolean[rowIndex.length - 1];
        Arrays.fill(isEnd, false);

        for(int i = 0; i < rowIndex.length - 1; i ++)
            if(rowIndex[i] == rowIndex[i + 1])
                isEnd[i] = true;
        for(int i = 0; i < getNumVertex(); i ++)
            if(isEnd[i]) endPoints.add(i);
    }

    public int getNumDegree(int i){
        return rowIndex[i + 1] - rowIndex[i];
    }

    public int getNumVertex() {
        return rowIndex.length - 1;
    }

}
