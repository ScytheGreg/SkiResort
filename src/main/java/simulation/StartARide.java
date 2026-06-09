package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.Edge;

import java.time.LocalTime;

/**
 * Represents the event of an athlete starting a journey on a specific Edge
 * (either skiing down a run or boarding a lift).
 */
public class StartARide extends AthleteEvent {

    public StartARide(LocalTime time, Athlete athlete, Edge edge) {
        super(time, athlete, edge);
    }

    /**
     * Executes the logic for starting a ride.
     * 1. Updates statistics for the specific Edge (SkiRun/SkiLift).
     * 2. Moves the athlete's position to the destination.
     * 3. Schedules the future 'EndARide' event based on the edge's travel time.
     * * @param seed Random seed (passed down from the simulation loop).
     * @return A Queue containing the EndARide event scheduled for the future.
     */
    @Override
    public Queue performAction(int seed) {
        // Log the event if the athlete is tracked (via AthleteEvent logic)
        Queue result = super.performAction(seed);

        // Increment the total number of people who have used this run/lift
        // This is critical for SkiRun surface degradation (smoothness)
        getEdge().takeARide(getAthlete());

        /* * Logic: We schedule the end of this ride.
         * The end time = current time + the constant rideTime of the edge.
         */
        LocalTime arrivalTime = getTime().plusSeconds(getEdge().getRideTime());

        // Create and schedule the corresponding EndARide event
        result.push(new EndARide(arrivalTime, getAthlete(), getEdge()));

        return result;
    }

    public boolean doesMoveAthlete(){
        return false;
    }

    /**
     * Formats the log message.
     * Example: "At 09:15:00 the athlete nr     1 has begun ride on ski run nr 3"
     */
    @Override
    public String toString() {
        // "has begun ride" matches the length of other event descriptions
        // to maintain column alignment in logs.
        return super.toString() + " has begun ride on " + getEdge().getName() + " nr " + getEdge().getId();
    }
}