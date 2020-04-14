import Traversal.BFSGraphTraversal;
import Traversal.DFSGraphTraversal;
import Traversal.Graph;
import util.GraphGenerator;
import util.GraphProcessor;
import util.RandomTimeGenerator;
import util.dagGen.DAGSmith;
import util.dagGen.DAGTools;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class Utility {
    public static void main(String[] args) {
        DAGSmith smith = new DAGSmith();
        RandomTimeGenerator randomTimeGenerator = new RandomTimeGenerator();
        GraphGenerator graphGenerator = new GraphGenerator();

        boolean[][] dag = smith.generateRandomDAG(10, 15);

        GraphProcessor graphProcessor = new GraphProcessor(dag);
        List<Integer> starts = graphProcessor.findStarts();
        List<Integer> ends = graphProcessor.findEnds();


        Timestamp[] timestamps = randomTimeGenerator.generateTimes(dag, starts);

        Graph graph = graphGenerator.generateGraph(dag, timestamps);
        graph.setStartPoints(starts);
        graph.setEndPoints(ends);


        System.out.println("start points: " + Arrays.toString(starts.toArray()));
        System.out.println("end points: " + Arrays.toString(ends.toArray()));


        System.out.println(DAGTools.printDAG(dag));
        System.out.println("Generated a " + dag.length + "x" + dag[0].length +
                " DAG with " + DAGTools.getEdges(dag) + " edges.");

        BFSGraphTraversal bfs = new BFSGraphTraversal(graph, null);
//        bfs.traversal(0);

        DFSGraphTraversal dfs = new DFSGraphTraversal(graph, null);

        Thread threadBFS = new Thread(bfs);
        Thread threadDFS = new Thread(dfs);
//        dfs.traversal(2);

        threadBFS.start();
        threadDFS.start();


    }


}

