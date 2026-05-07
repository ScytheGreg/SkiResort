package resort;


import java.util.Arrays;

public class SkiResort {
    private Node[] nodes;
    private int nodesNum;
    private SkiLift[] skiLifts;
    private int skiLiftsNum;
    private SkiRun[] skiRuns;
    private int skiRunsNum;

    public SkiResort(Node[] nodes, int nodesNum, SkiLift[] skiLifts, int skiLiftsNum, SkiRun[] skiRuns, int skiRunsNum) {
        this.nodes = nodes;
        this.nodesNum = nodesNum;
        this.skiLifts = skiLifts;
        this.skiLiftsNum = skiLiftsNum;
        this.skiRuns = skiRuns;
        this.skiRunsNum = skiRunsNum;
        connectResortGraph();
    }

    private void countNodesFromEdges(int[] cntResult, Edge[] edges) {
        Arrays.fill(cntResult, 0);
        for (Edge edge : edges) {
            cntResult[edge.getBeginning().getId()]++;
        }
    }

    private void initNodesArrays(Edge[][] edgeInNodes, int[] edgeCnt, int N) {
        for (int i = 0; i < N; ++i) {
            edgeInNodes[i] = new Edge[edgeCnt[i]];
        }
    }

    private void connectEdgesLifts(SkiLift[] lifts, int N, SkiLift[][] liftsInNodes) {
        int[] liftCnt = new int[N];

        // count how many lifts per node
        countNodesFromEdges(liftCnt, lifts);

        // allocate per node
        for (int i = 0; i < N; ++i) {
            liftsInNodes[i] = new SkiLift[liftCnt[i]];
        }

        // fill arrays
        for (SkiLift lift : lifts) {
            int nodeId = lift.getBeginning().getId();
            liftsInNodes[nodeId][--liftCnt[nodeId]] = lift;
        }
    }

    private void connectEdgesRuns(SkiRun[] runs, int N, SkiRun[][] runsInNodes) {
        int[] runCnt = new int[N];

        // count how many runs per node
        countNodesFromEdges(runCnt, runs);

        // allocate per node
        for (int i = 0; i < N; ++i) {
            runsInNodes[i] = new SkiRun[runCnt[i]];
        }

        // fill arrays
        for (SkiRun run : runs) {
            int nodeId = run.getBeginning().getId();
            runsInNodes[nodeId][--runCnt[nodeId]] = run;
        }
    }


    public void connectResortGraph() {
        int N = getNodesNum();

        SkiLift[][] skiLiftsInNodes = new SkiLift[N][];
        connectEdgesLifts(getSkiLifts(), N, skiLiftsInNodes);

        SkiRun[][] skiRunsInNodes = new SkiRun[N][];
        connectEdgesRuns(getSkiRuns(), N, skiRunsInNodes);

        Node[] nodes = getNodes();
        for(int i = 0 ; i < N ;++i){
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

    private void logEdges(Edge[] edges){
        for(Edge edge : edges){
            edge.log();
        }
    }

    public void log(){
        logEdges(skiLifts);
        logEdges(skiRuns);
    }
}
