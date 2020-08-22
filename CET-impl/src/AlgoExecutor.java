import Components.Graph;
import Traversal.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class AlgoExecutor {
    GraphTraversal [] traversalAlgos = new GraphTraversal[100];
    int length;
    Graph graph;
    public AlgoExecutor(Graph graph){
        length = 0;
        this.graph = graph;
        Arrays.fill(traversalAlgos, null);
    }

    /**
     * Selection of algo to initialize
     *      1. Normal BFS
     *      2. Normal DFS
     *      3. Sequential Hybrid
     *      4. M_CET
     *      5. T_CET
     *      6. BFS with Stack
     * @param algo
     * @return
     */
    public void addAlgo(int algo){

        switch (algo){
            case 1: traversalAlgos[length] = new BFSGraphTraversal(graph, null); break;

            case 2: traversalAlgos[length] = new DFSGraphTraversal(graph, null); break;

            case 3: addSeqHybrid(); break;

            case 4: traversalAlgos[length] = new M_CETGraphTraversal(graph, null); break;

            case 5: traversalAlgos[length] = new T_CETGraphTraversal(graph, null); break;

            default: return;
        }

        length ++;

    }

    public void addSeqHybrid(){
        System.out.println(" \n" +
                "- As you selected hybrid type, \n" +
                "- please specify the anchor nodes selection strategy:\n" +
                "    1. Random" +
                "    2. Largest degree nodes");
        Scanner sc = new Scanner(System.in);
        int selection = Integer.parseInt(sc.nextLine());
        // TODO: select anchor node method to write

        ArrayList<Integer> anchor = new ArrayList<>();
        anchor.add(0);
        anchor.add(3);
        anchor.add(6);

        traversalAlgos[length] = new SeqHybridGraphTraversal(graph, null,anchor);

    }

    public void runAlgos(){
        for(GraphTraversal algo: traversalAlgos){
            if (algo!= null){
                System.out.println("Algo to execute: " + algo.getClass().getName());

                algo.execute();
            }
        }
        writeTimeResult();
    }

    public void writeTimeResult(){
        File file = new File("OutputFiles/result/timeResults/" + "graph"+ graph.getNumVertex() + "-" + new Date().toString() +".txt");

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);

            for(GraphTraversal algo: traversalAlgos){
                if(algo != null){
                    fw.write(algo.getClass().getName() + "Execution in nanoseconds  : " + algo.timeElapsed + "\n");
                    System.out.println(algo.getClass().getName() + "Execution in nanoseconds  : " + algo.timeElapsed);
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
