package edge;

import node.Node;
import containers.Queue;

public class SkiLift extends Edge{
    private Queue waiting;
    int capacity;
    int frequency;
    public SkiLift(String id, Node begging, Node end,
                  int rideTime, int capacity, int frequency) {
        super(id, begging, end, rideTime);
        this.capacity = capacity;
        this.frequency = frequency;
        this.waiting = new Queue();
    }

}
