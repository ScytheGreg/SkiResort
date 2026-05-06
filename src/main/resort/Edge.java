package resort;

public abstract class Edge {
    private int id;
    private Node beginning;
    private Node end;
    private int rideTime;

    public Edge(Node beginning, Node end, int rideTime, int id) {
        this.id = id;
        this.beginning = beginning;
        this.end = end;
        this.rideTime = rideTime;
    }

    public int getId() {
        return id;
    }

    public Node getBeginning() {
        return beginning;
    }

    public Node getEnd() {
        return end;
    }

    public int getRideTime() {
        return rideTime;
    }
}