import util.*;
import util.dagGen.DAGSmith;
import util.dagGen.DAGTools;
import Traversal.*;

import java.sql.Timestamp;

public class Utility {
    public static void main(String []args){
        DAGSmith smith = new DAGSmith();
        RandomTimeGenerator randomTimeGenerator = new RandomTimeGenerator();
        GraphGenerator graphGenerator = new GraphGenerator();


        boolean[][] dag = smith.generateRandomDAG(2, 1);

        Timestamp [] timestamps = randomTimeGenerator.generateTimes(dag);
        Graph graph = graphGenerator.generateGraph(dag, timestamps);



        System.out.println(DAGTools.printDAG(dag));
        System.out.println("Generated a " + dag.length + "x" + dag[0].length +
                " DAG with " + DAGTools.getEdges(dag) + " edges.");





    }


}

