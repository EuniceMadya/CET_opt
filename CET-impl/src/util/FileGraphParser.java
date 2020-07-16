package util;

import Components.Graph;

import java.io.File;
import java.sql.Timestamp;
import java.util.Scanner;

public class FileGraphParser {
    // graph are read from a text file, with the format of adjacency matrix representation

    /**
     * Example:
     * <p>
     * 3
     * 1,0,0
     * 0,0,0
     * 0,1,0
     * <p>
     * 2000-01-01 00:00:00
     * 2000-02-01 00:00:01
     * 2000-03-01 12:00:00
     */

    public Graph readGraph(String fileName) {
        Graph graph;
        Timestamp[] timestamps = null;
        boolean[][] grid = null;
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            int nodeNum = Integer.parseInt(myReader.nextLine());

            grid = new boolean[nodeNum][nodeNum];


            for (int i = 0; i < nodeNum; i++) {
                String data = myReader.nextLine();
                String[] neighbours = data.split(",");
                for (int j = 0; j < nodeNum; j++) {
                    grid[i][j] = neighbours[j].equals("1");
                }
            }
            if (myReader.nextLine().equals("true")) {
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

        graph = graphGenerator.buildGraph(grid, null);

        return graph;
    }

}
