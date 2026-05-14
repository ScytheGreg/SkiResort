package simulation;

import containers.Queue;
import resort.SkiLift;

import java.time.LocalTime;

/**
 * Represents a periodic event where a ski lift chair or cabin arrives
 * at the bottom station to pick up waiting athletes.
 */
public class PickUpPassengers extends Event {
    // The specific lift associated with this pickup cycle
    private final SkiLift lift;

    public PickUpPassengers(LocalTime time, SkiLift lift) {
        super(time);
        this.lift = lift;
    }

    /**
     * Executes the lift operation logic.
     * 1. Asks the SkiLift object to process its waiting queue.
     * 2. The SkiLift will return a Queue containing:
     * - StartARide events for athletes who successfully boarded.
     * - A new PickUpPassengers event scheduled for the next cycle (time + frequency).
     * * @param seed Random seed (passed for consistency, though lifts are currently deterministic).
     * @return A Queue of events to be added to the simulation's PriorityQueue.
     */
    @Override
    public Queue performAction(int seed) {
        /*
         * This delegates the heavy lifting to the SkiLift class.
         * The SkiLift logic handles both boarding and the scheduling
         * of the next "PickUpPassengers" event.
         */
        return lift.pickUpPassengers(getTime());
    }

    /**
     * Formats the log message.
     * Note: Typically, the project might not require tracking lift cycles
     * unless an athlete is involved, but this is useful for debugging
     * to ensure lifts are running.
     */
    @Override
    public String toString() {
        return super.toString() + " lift nr " + lift.getId() + " is picking up passengers.";
    }
}