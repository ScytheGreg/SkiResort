package resort;

/**
 * Represents a vertex (węzeł) in the resort's directed graph.
 * Nodes represent points where ski runs and lifts start or end.
 */
public class Node {
    // Basic coordinates and identification
    private final int id;
    private final int height; // Elevation (meters above sea level)
    private final int lat;    // Latitude
    private final int lon;    // Longitude

    /**
     * Indicates if the node is "communicated" (skomunikowany).
     * Usually, this means the node is accessible from the outside world (e.g., a parking lot)
     * and can serve as a starting point for athletes.
     */
    private final boolean isCommunicated;

    // Outgoing edges from this node
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

    /**
     * Sets the list of lifts starting at this node.
     * Used during the initialization of the resort graph.
     */
    public void setSkiLifts(SkiLift[] skiLifts) {
        this.skiLifts = skiLifts;
    }

    /**
     * Sets the list of ski runs starting at this node.
     * Used during the initialization of the resort graph.
     */
    public void setSkiRuns(SkiRun[] skiRuns) {
        this.skiRuns = skiRuns;
    }

    public SkiRun[] getSkiRuns() {
        return skiRuns;
    }

    public SkiLift[] getSkiLifts() {
        return skiLifts;
    }

    public int getHeight() {
        return height;
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }

    /**
     * Returns an array containing this node and all nodes reachable
     * directly by taking a single lift from here.
     * * This is useful for the "Rational Decision" logic, where an athlete
     * evaluates runs available at the current node AND runs available
     * at the top of the lifts starting here.
     */
    public Node[] getLiftNeighbours() {
        // Result size is lifts + 1 (to include the current node itself)
        Node[] result = new Node[skiLifts.length + 1];
        result[0] = this;

        for (int i = 1; i <= skiLifts.length; ++i) {
            // Index i-1 because the loop starts at 1
            result[i] = skiLifts[i - 1].getEnd();
        }
        return result;
    }
}