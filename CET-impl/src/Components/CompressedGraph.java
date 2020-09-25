package Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompressedGraph{
    public int[] colIndex;
    public int[] rowIndex;

    private boolean[] isEndPoints;
    private boolean[] isStartPoints;

    private int numOfStartPoint;
    private int numOfEndPoint;

    private ArrayList<Integer> startPoints;
    private ArrayList<Integer> endPoints;

    public CompressedGraph(int colNum, int rowNum) {
        colIndex = new int[colNum];
        rowIndex = new int[rowNum];
        startPoints = new ArrayList<>();
        endPoints = new ArrayList<>();

        numOfEndPoint = 0;
        numOfStartPoint = 0;

        Arrays.fill(colIndex, -1);
        Arrays.fill(rowIndex, -1);
    }

    public int[] getColIndex() {
        return colIndex;
    }

    public int[] getRowIndex() {
        return rowIndex;
    }

    public int getStartPointNum(){
        if(numOfStartPoint == 0) loadStartPoints();
        return numOfStartPoint;
    }

    public int getEndPointNum(){
        if(numOfEndPoint == 0) loadEndPoints();
        return numOfEndPoint;
    }

    public boolean startContains(int i){
        return isStartPoints[i];
    }

    public boolean endContains(int i){
        return isEndPoints[i];
    }


    public List<Integer> getStartPoints() {
        if(startPoints.size() == 0) loadStartPoints();
        return startPoints;
    }

    private void loadStartPoints(){
        isStartPoints = new boolean[rowIndex.length - 1];
        Arrays.fill(isStartPoints, true);

        for(int i : colIndex)  isStartPoints[i] = false;
        for(int i = 0; i < getNumVertex(); i ++)
            if(isStartPoints[i]) {
                startPoints.add(i);
                numOfStartPoint ++;
            }
    }

    public List<Integer> getEndPoints() {
        if(endPoints.size() == 0) loadEndPoints();

        return endPoints;
    }

    private void loadEndPoints(){
        isEndPoints  = new boolean[rowIndex.length - 1];
        Arrays.fill(isEndPoints, false);

        for(int i = 0; i < rowIndex.length - 1; i ++)
            if(rowIndex[i] == rowIndex[i + 1])
                isEndPoints[i] = true;
        for(int i = 0; i < getNumVertex(); i ++)
            if(isEndPoints[i]) {
                endPoints.add(i);
                numOfEndPoint ++;
            }
    }

    public int getNumDegree(int i){
        return rowIndex[i + 1] - rowIndex[i];
    }

    public int getTotalNumEdges(){
        return colIndex.length;
    }

    public int getNumVertex() {
        return rowIndex.length - 1;
    }
}
