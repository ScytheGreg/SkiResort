package resort;


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
