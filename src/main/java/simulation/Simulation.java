package simulation;

import java.time.LocalTime;

import containers.PriorityQueue;
import containers.Queue;
import containers.emptyContainerException;
import resort.*;

/**
 * The main engine of the ski resort simulation.
 * It manages the timeline and orchestrates interactions between
 * the infrastructure (SkiResort) and the agents (Athletes).
 */
public class Simulation {
    // The simulation begins at 09:00:00
    static final private LocalTime START_TIME = LocalTime.of(9, 0);

    /* * The simulation loop stops processing events that occur at or after 16:00:00.
     * Note: New decisions stop at 15:00:00, but athletes can finish rides until 16:00:00.
     */
    static final private LocalTime END_TIME = LocalTime.of(16, 0);

    // Chronological container of all future events
    private final PriorityQueue events;

    /**
     * Initializes the simulation.
     * @param events The priority queue, already pre-filled with athlete arrival events.
     * @param resort The infrastructure used to initialize lift operations.
     */
    public Simulation(PriorityQueue events, SkiResort resort) {
        this.events = events;
        // Lifts start operating independently at the start of the day
        loadPickUpEvents(resort.getSkiLifts());
    }

    /**
     * Schedules the very first pickup event for every lift in the resort.
     * These events will then self-perpetuate until closing time.
     */
    public void loadPickUpEvents(SkiLift[] lifts) {
        for (SkiLift lift : lifts) {
            events.push(new PickUpPassengers(START_TIME, lift));
        }
    }

    /**
     * The core simulation loop.
     * 1. Extracts the earliest event from the PriorityQueue.
     * 2. Executes the event's action.
     * 3. Adds any resulting new events back into the PriorityQueue.
     * 4. Repeats until the queue is empty or the simulation end time is reached.
     * * @param seed Used for deterministic random decision-making.
     * @param resort The resort object, used for the final summary logging.
     * @throws emptyContainerException If an attempt is made to take from an empty queue.
     */
    public void simulate(int seed, SkiResort resort) throws emptyContainerException {

        // Loop as long as there are things happening before the resort's hard close
        while (!events.isEmpty() && events.getFront().getTime().isBefore(END_TIME)) {

            // 1. Get the next chronological event
            Event currentEvent = events.takeFront();

            // 2. Perform the action (e.g., boarding a lift, finishing a run)
            // This returns a Queue of events triggered by this action
            Queue newEvents = currentEvent.performAction(seed);

            // 3. Merge the new events into the main PriorityQueue
            while (!newEvents.isEmpty()) {
                events.push(newEvents.takeFront());
            }
        }

        // After the simulation loop ends, print the daily statistics for all edges
        resort.log();
    }
}