package util;

import Components.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class FileGraphParser {
    // graph are read from a text file, with the format of adjacency matrix representation


    public Graph readGraph(String fileName) {
        File myObj = new File(fileName);
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String type = myReader.nextLine();
        myReader.close();

        if (type.contains("Pair")) return readCompressedPairGraph(fileName);
        else if (type.contains("Grid")) return readGridGraph(fileName);
        else if (type.contains("Lists")) return readCompressedListGraph(fileName);

        System.out.println("WARNING: INVALID GRAPH INPUT FILE!");
        return null;
    }

    /**
     * Example:
     * <p>
     * Pair
     * 4
     * 0,2
     * 1,2
     * 1,3
     * 2,3
     */
    private Graph readCompressedPairGraph(String fileName) {
        Graph graph;
        int nodeNum = 0;
        ArrayList<int[]> matrix = new ArrayList<>();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String type = myReader.nextLine();
            if (type.contains("Random") || !type.contains("Sparse")) {
                System.out.println("ERROR: Read wrong type of graph! Should be Compressed Pair!");
            }

            nodeNum = Integer.parseInt(myReader.nextLine());
            while (myReader.hasNext()) {
                String[] data = myReader.nextLine().split(",");
                if (data.length != 2) break;
                matrix.add(new int[]{Integer.parseInt(data[0]), Integer.parseInt(data[1])});
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("File parsing error for reading sparse matrix.");
            e.printStackTrace();
        }
        GraphGenerator graphGenerator = new GraphGenerator();
        graph = graphGenerator.buildGraph(matrix, nodeNum);

        return graph;
    }

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
    private Graph readGridGraph(String fileName) {
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

    /**
     * Example:
     * <p>
     * List
     * 5
     * 1,2
     * 3
     * 3,4
     * 4
     * NaN
     */
    private Graph readCompressedListGraph(String fileName) {
        ArrayList<Integer>[] lists = null;
        Graph graph;
        int counter = 0;
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String type = myReader.nextLine();
            if (type.contains("Random") || !type.contains("Sparse")) {
                System.out.println("ERROR: Read wrong type of graph! Should be Sparse matrix!");
            }
            lists = new ArrayList[Integer.parseInt(myReader.nextLine())];

            while (myReader.hasNext()) {
                String data = myReader.nextLine();
                if (data.equalsIgnoreCase("NaN")) {
                    counter++;
                    continue;
                }
                String[] neighbours = data.split(",");
                lists[counter] = new ArrayList<>();
                for (String neighbour : neighbours) lists[counter].add(Integer.parseInt(neighbour));
                counter ++;
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("File parsing error for reading sparse matrix.");
            e.printStackTrace();
        }
        GraphGenerator graphGenerator = new GraphGenerator();
        graph = graphGenerator.buildGraph(lists);

        return graph;
    }


}
