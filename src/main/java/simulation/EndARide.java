package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.Edge;

import java.time.LocalTime;

/**
 * Represents the moment an athlete finishes a ride (either a ski run or a lift).
 * This event marks the transition from movement back to the decision-making state.
 */
public class EndARide extends AthleteEvent {

    public EndARide(LocalTime time, Athlete athlete, Edge edge) {
        super(time, athlete, edge);
    }

    /**
     * Executes the logic for ending a ride.
     * 1. Updates the athlete's position in the resort graph.
     * 2. Logs the activity if the athlete is tracked.
     * 3. Triggers the athlete to make a new decision for their next move.
     * * @param seed Random seed for decision consistency.
     * @return A Queue containing the next scheduled event (e.g., StartARide or QueueUp).
     */
    @Override
    public Queue performAction(int seed) {
        // First, update the athlete's current node to the end of the edge they just traveled.
        getAthlete().move(getEdge());

        // Call super to handle the 'isTracked' console logging logic.
        Queue result = super.performAction(seed);

        /* * Important: After finishing a ride, the athlete is now at a node
         * and must decide what to do next based on attractiveness or impulsivity.
         */
        Event nextEvent = getAthlete().makeDecision(seed, getTime());

        // If the decision is not null (e.g., it's before 15:00), schedule it.
        if (nextEvent != null) {
            result.push(nextEvent);
        }

        return result;
    }

    public boolean doesMoveAthlete(){
        return false;
    }

    /**
     * Formats the log message.
     * Example: "At 10:30:00 the athlete nr     1 has end   ride on ski run nr 5"
     */
    @Override
    public String toString() {
        // "has end   ride" is padded with spaces to match the length of "started ride"
        // for aligned logs.
        return super.toString() + " has end   ride on " + getEdge().getName() + " nr " + getEdge().getId();
    }
}