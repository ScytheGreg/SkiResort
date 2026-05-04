package resort;

public class Edge {
    private String id;
    private Node begging;
    private Node end;
    private int rideTime;

    public Edge(String id, Node begging, Node end, int rideTime){
        this.id = id; this.begging = begging; this.end = end; this.rideTime = rideTime;
    }

}
