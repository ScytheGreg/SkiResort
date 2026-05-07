package resort;

public class Node {
    private final int id, height, lat, lon;
    private final boolean isCommunicated;

    private SkiRun[] skiRuns;
    private SkiLift[] skiLifts;

    public Node(int id, int height, int lat, int lon, boolean isCommunicated) {
        this.id = id;
        this.height = height;
        this.lat = lat;
        this.lon = lon;
        this.isCommunicated = isCommunicated;
    }

    public int getId() {
        return id;
    }

    public boolean isCommunicated() {
        return isCommunicated;
    }

    public void setSkiLifts(SkiLift[] skiLifts) {
        this.skiLifts = skiLifts;
    }

    public void setSkiRuns(SkiRun[] skiRuns) {
        this.skiRuns = skiRuns;
    }

    public SkiRun[] getSkiRuns() {
        return skiRuns;
    }

    public SkiLift[] getSkiLifts() {
        return skiLifts;
    }

    public Node[] getLiftNeigbours(){
        Node[] result = new Node[skiLifts.length + 1];
        result[0] = this;
        for(int i = 1 ; i <= skiLifts.length ; ++i){
            result[i] = skiLifts[i - 1].getEnd();
        }
        return result;
    }
}
