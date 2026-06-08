package resort;

import athlete.Athlete;
import simulation.Event;

import java.time.LocalTime;

/**
 * Represents a directed connection in the ski resort graph.
 * This is an abstract base for both downhill runs and uphill lifts.
 */
public abstract class Edge {
    private final int id;               // Unique identifier for the edge [cite: 207]
    private final Node beginning;       // Starting station or point [cite: 9, 215, 217]
    private final Node end;             // Ending station or point [cite: 9, 215, 217]
    private final int rideTime;         // Constant travel time in virtual seconds

    // Statistics counter for the end-of-day report
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

    /**
     * Returns the time it takes to traverse this edge.
     * Note: This time is constant and independent of athlete skill.
     */
    public int getRideTime() {
        return rideTime;
    }

    /**
     * Returns the total number of athletes who used this edge during the simulation.
     */
    public int getServedAthletesCnt() {
        return servedAthletesCnt;
    }

    /**
     * Increments the counter whenever an athlete completes a ride or starts a trip,
     * depending on your simulation's event timing.
     */
    public void takeARide() {
        ++servedAthletesCnt;
    }

    /**
     * Generates the daily summary lines for this specific edge.
     * To be used at the end of the simulation.
     */
    public String[] getLog() {
        String[] message = new String[2];
        message[0] = "Daily summary of " + getName() + " nr " + getId();
        message[1] = "\t Number of rides: " + getServedAthletesCnt();
        return message;
    }

    /**
     * Returns the type of edge (e.g., "Trasa" or "Wyciąg") for reporting purposes.
     */
    abstract public String getName();

    /**
     * Prints the summary statistics to standard output.
     */
    public void log() {
        for (String line : getLog()) {
            System.out.println(line);
        }
    }

    /**
     * Adds an athlete to the appropriate waiting mechanism.
     * For lifts, this involves a queue[cite: 32, 145].
     * For runs, this might start the ride immediately.
     */
    abstract public void addWaiting(Athlete athlete);
    abstract public Event beChoosenByAthlete(Athlete athlete, LocalTime time);
}