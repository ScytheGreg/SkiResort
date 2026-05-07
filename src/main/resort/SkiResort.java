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

    private void initNodesArrrays(Edge[][] edgeInNodes, int[] edgeCnt, int N) {
        for (int i = 0; i < N; ++i) {
            edgeInNodes[i] = new Edge[edgeCnt[i]];
        }
    }

    private void countNodesArrays(Edge[][] edgeInNodes, int[] edgeCnt, Edge[] edges){
        for (Edge edge : edges){
            int nodeId = edge.getBeginning().getId();
            edgeInNodes[nodeId][--edgeCnt[nodeId]] = edge;
        }
    }

    private void connectEdges(Edge[] edges, int N, Edge[][] edgesInNodes){
        int[] edgeCnt = new int[N]; // Count lifts in nodes
        countNodesFromEdges(edgeCnt, edges);
        initNodesArrrays(edgesInNodes, edgeCnt, N);
        countNodesArrays(edgesInNodes, edgeCnt, edges);
    }


    public void connectResortGraph() {
        int N = getNodesNum();

        SkiLift[][] skiLiftsInNodes = new SkiLift[N][];
        connectEdges(getSkiLifts(), N, skiLiftsInNodes);

        SkiRun[][] skiRunsInNodes = new SkiRun[N][];
        connectEdges(getSkiRuns(), N, skiRunsInNodes);

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
}
