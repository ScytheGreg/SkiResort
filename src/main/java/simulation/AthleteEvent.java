package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.Edge;

import java.time.LocalTime;

/**
 * A specialized type of Event that involves a specific athlete and a specific path (Edge).
 * This class handles the common "tracking" logic required by the project.
 */
abstract public class AthleteEvent extends Event {
    private final Athlete athlete;
    private final Edge edge;

    public AthleteEvent(LocalTime time, Athlete athlete, Edge edge) {
        super(time);
        this.athlete = athlete;
        this.edge = edge;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Edge getEdge() {
        return edge;
    }

    /**
     * Composes a log message starting with the timestamp, followed by the athlete ID.
     * The ID is formatted to 5 spaces to keep logs aligned.
     */
    @Override
    public String toString() {
        return super.toString() + " the athlete nr " + String.format("%5d", athlete.getId());
    }

    /**
     * Executes the shared logic for athlete-related events.
     * * In this simulation, if an athlete is marked as 'tracked', every event
     * they participate in must be printed to the standard output.
     * * @return An empty Queue. Subclasses (like FinishARide) will override
     * this and return actual events.
     */
    @Override
    public Queue performAction(int seed) {
        // Project requirement: Log activity only if isTracked is true
        if (athlete.isTracked()) {
            System.out.println(this.toString());
        }

        // Default behavior returns an empty queue of new events
        return new Queue();
    }
}