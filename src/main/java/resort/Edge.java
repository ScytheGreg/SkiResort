package resort;

import athlete.Athlete;

public abstract class Edge {
    private final int id;
    private final Node beginning;
    private final Node end;
    private final int rideTime;

    private int servedAthletesCnt = 0;

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

    public int getServedAthletesCnt() {
        return servedAthletesCnt;
    }

    public void takeARide() {
        ++servedAthletesCnt;
    }

    public String[] getLog() {
        String[] message = new String[2];
        message[0] = "Daily summary of " + getName() + " nr " + getId();
        message[1] = "\t Number of rides: " + getServedAthletesCnt();
        return message;
    }

    abstract public String getName();

    public void log() {
        for (String line : getLog()) {
            System.out.println(line);
        }
    }

    abstract public void addWaiting(Athlete athlete);

}