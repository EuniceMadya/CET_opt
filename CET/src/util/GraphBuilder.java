package util;

import Components.Graph;
import util.dagGen.DAGSmith;
import util.dagGen.DAGTools;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class GraphBuilder {

    GraphGenerator graphGenerator;

    public GraphBuilder() {
        graphGenerator = new GraphGenerator();
    }

    public Graph generateRandomGraph(int num) {

        RandomTimeGenerator randomTimeGenerator = new RandomTimeGenerator();
        DAGSmith smith = new DAGSmith();

        boolean[][] dag = smith.generateRandomDAG(num, 150);

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

        return graph;

    }


    public Graph generateGraphFileFile(String path) {
        FileGraphParser fileGraphParser = new FileGraphParser();

        return fileGraphParser.readGraph(path);
    }

    public Graph generateGraph(String type) {
        Graph graph;


        if (type.equalsIgnoreCase("random")) {
            graph = generateRandomGraph(20);
        } else {
            graph = generateGraphFileFile(type);
        }

        return graph;
    }
}
