package resort;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Manages the entire infrastructure of the ski resort.
 * Its primary responsibility is to "connect" the graph by populating each Node
 * with its respective outgoing SkiRuns and SkiLifts.
 */
public class SkiResort {
    private final Node[] nodes;
    private final int nodesNum;
    private final SkiLift[] skiLifts;
    private final int skiLiftsNum;
    private final SkiRun[] skiRuns;
    private final int skiRunsNum;

    HashSet<Edge> edges = new HashSet<>();

    public SkiResort(Node[] nodes, int nodesNum, SkiLift[] skiLifts, int skiLiftsNum, SkiRun[] skiRuns, int skiRunsNum) {
        this.nodes = nodes;
        this.nodesNum = nodesNum;
        this.skiLifts = skiLifts;
        this.skiLiftsNum = skiLiftsNum;
        this.skiRuns = skiRuns;
        this.skiRunsNum = skiRunsNum;
        edges.addAll(Arrays.stream(skiRuns).toList());
        edges.addAll(Arrays.stream(skiLifts).toList());

        // Build the adjacency list within the nodes immediately upon creation
        connectResortGraph();
    }

    /**
     * Helper to count how many edges (lifts or runs) start at each node.
     * Used to determine the exact size of arrays needed for each Node.
     */
    private void countNodesFromEdges(int[] cntResult, Edge[] edges) {
        Arrays.fill(cntResult, 0);
        for (Edge edge : edges) {
            // Increments the counter for the node where the edge begins
            cntResult[edge.getBeginning().getId()]++;
        }
    }

    /**
     * Groups individual SkiLifts into arrays corresponding to their starting nodes.
     */
    private void connectEdgesLifts(SkiLift[] lifts, int N, SkiLift[][] liftsInNodes) {
        int[] liftCnt = new int[N];

        // 1. Count frequencies to know array sizes
        countNodesFromEdges(liftCnt, lifts);

        // 2. Allocate the exact amount of memory needed for each node's lift array
        for (int i = 0; i < N; ++i) {
            liftsInNodes[i] = new SkiLift[liftCnt[i]];
        }

        /* 3. Fill the allocated arrays.
         * Using --liftCnt[nodeId] as an index is a common trick to fill the array
         * backwards while tracking remaining spots.
         */
        for (SkiLift lift : lifts) {
            int nodeId = lift.getBeginning().getId();
            liftsInNodes[nodeId][--liftCnt[nodeId]] = lift;
        }
    }

    /**
     * Groups individual SkiRuns into arrays corresponding to their starting nodes.
     */
    private void connectEdgesRuns(SkiRun[] runs, int N, SkiRun[][] runsInNodes) {
        int[] runCnt = new int[N];

        countNodesFromEdges(runCnt, runs);

        for (int i = 0; i < N; ++i) {
            runsInNodes[i] = new SkiRun[runCnt[i]];
        }

        for (SkiRun run : runs) {
            int nodeId = run.getBeginning().getId();
            runsInNodes[nodeId][--runCnt[nodeId]] = run;
        }
    }

    /**
     * The main "glue" method. It transforms a flat list of edges into a
     * structured graph where each Node knows its available paths.
     */
    public void connectResortGraph() {
        int N = getNodesNum();

        // Temporary 2D arrays to hold edges grouped by node
        SkiLift[][] skiLiftsInNodes = new SkiLift[N][];
        connectEdgesLifts(getSkiLifts(), N, skiLiftsInNodes);

        SkiRun[][] skiRunsInNodes = new SkiRun[N][];
        connectEdgesRuns(getSkiRuns(), N, skiRunsInNodes);

        // Update each Node object with its respective arrays of runs and lifts
        Node[] nodes = getNodes();
        for (int i = 0; i < N; ++i) {
            nodes[i].setSkiRuns(skiRunsInNodes[i]);
            nodes[i].setSkiLifts(skiLiftsInNodes[i]);
        }
    }

    public Node[] getNodes() {
        return nodes;
    }

    public int getNodesNum() {
        return nodesNum;
    }

    public SkiLift[] getSkiLifts() {
        return skiLifts;
    }

    public int getSkiLiftsNum() {
        return skiLiftsNum;
    }

    public SkiRun[] getSkiRuns() {
        return skiRuns;
    }

    public int getSkiRunsNum() {
        return skiRunsNum;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    /**
     * Triggers the daily summary report for every lift and ski run in the resort.
     * Called at the end of the simulation.
     */
    public void log() {
        for (Edge edge : edges){
            edge.log();
        }
    }
}