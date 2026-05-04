package edge;

import node.Node;

public class SkiRun extends Edge {
    private int advanceLevel;
    private double toughness;

    public SkiRun(String id, Node begging, Node end,
                  int rideTime, int advanceLevel, double toughness) {
        super(id, begging, end, rideTime);
        this.advanceLevel = advanceLevel;
        this.toughness = toughness;
    }
}
