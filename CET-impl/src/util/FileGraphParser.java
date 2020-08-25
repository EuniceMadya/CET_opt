package util;

import Components.Graph;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class FileGraphParser {
    // graph are read from a text file, with the format of adjacency matrix representation

    /**
     * Example:
     * <p>
     * Grid
     * 3
     * 1,0,0
     * 0,0,0
     * 0,1,0
     * <p>
     * 2000-01-01 00:00:00
     * 2000-02-01 00:00:01
     * 2000-03-01 12:00:00
     */

    public Graph readGraph(String fileName, boolean sparse) {
        if (sparse) return readSparseMatrixGraph(fileName);

        return readGridGraph(fileName);
    }

    public Graph readSparseMatrixGraph(String fileName) {
        Graph graph;
        int nodeNum = 0;
        ArrayList<int[]> matrix = new ArrayList<>();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String type = myReader.nextLine();
            if (type.contains("Random") || !type.contains("Sparse")) {
                System.out.println("ERROR: Read wrong type of graph! Should be Sparse matrix!");
            }

            nodeNum = Integer.parseInt(myReader.nextLine());
            while (myReader.hasNext()) {
                String[] data = myReader.nextLine().split(",");
                matrix.add(new int[]{Integer.parseInt(data[0]), Integer.parseInt(data[1])});
            }
        } catch (Exception e) {
            System.out.println("File parsing error for reading sparse matrix.");
            e.printStackTrace();
        }
        GraphGenerator graphGenerator = new GraphGenerator();
        graph = graphGenerator.buildGraph(matrix, nodeNum);
        return graph;
    }

    public Graph readGridGraph(String fileName) {
        Graph graph;
        Timestamp[] timestamps;
        boolean[][] grid = null;
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String type = myReader.nextLine();
            if (type.contains("Random") || type.contains("Sparse")) {
                System.out.println("ERROR: Read wrong type of graph! Should be grid!");
            }

            int nodeNum = Integer.parseInt(myReader.nextLine());

            grid = new boolean[nodeNum][nodeNum];


            for (int i = 0; i < nodeNum; i++) {
                String data = myReader.nextLine();
                String[] neighbours = data.split(",");
                for (int j = 0; j < nodeNum; j++) {
                    grid[i][j] = neighbours[j].equals("1");
                }
            }
            if (myReader.nextLine().equals("timestamp")) {
                timestamps = new Timestamp[nodeNum];
                for (int i = 0; i < nodeNum; i++) {
                    timestamps[i] = Timestamp.valueOf(myReader.nextLine());
                }
            }
            myReader.close();

        } catch (Exception e) {
            System.out.println("File parsing error.");
            e.printStackTrace();
        }
        GraphGenerator graphGenerator = new GraphGenerator();

        graph = graphGenerator.buildGraph(grid);

        return graph;
    }

}
