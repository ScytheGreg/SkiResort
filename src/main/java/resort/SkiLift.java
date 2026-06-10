package resort;

import athlete.Athlete;
import containers.Queue;
import containers.StatisticsQueue;
import simulation.*;

import java.time.LocalTime;

/**
 * Represents a ski lift that transports athletes from a lower node to a higher one.
 * Lifts operate on a periodic cycle (frequency) and have a limited number of seats (capacity).
 */
public class SkiLift extends Edge {
    // Queue of athletes waiting at the bottom station
    private final StatisticsQueue waiting;

    // Helper time to calculate sumOfLength only after queue change
    private LocalTime lastModification = LocalTime.of(9, 0);
    private long sumOfLength = 0;
    private long maximumPossibleThroughput = 0;

    // Maximum number of athletes that can enter a single chair/cabin
    private final int capacity;

    // Time interval (in seconds) between consecutive chairs/cabins
    private final int frequency;

    public SkiLift(Node beginning, Node end, int rideTime, int id,
                   int frequency, int capacity) {
        super(beginning, end, rideTime, id);

        assert (frequency != 0) : "Frequency cannot be zero to avoid infinite event loops";
        this.frequency = frequency;
        this.capacity = capacity;
        this.waiting = new StatisticsQueue(); // FIFO queue for fair waiting
    }

    @Override
    public String getName() {
        // Padded string for alignment in logs
        return "lift   ";
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFrequency() {
        return frequency;
    }

    /**
     * Implements updating sum of length of queue during the day.
     * Manage btw lastModification.
     *
     * @param time to which queue was not modified until last modification.
     * @return actual time average was calculated to
     */
    private LocalTime updateAverageStatics(LocalTime time) {
        // Final average is gained at 14:59:59
        if (time.isAfter(LocalTime.of(14, 59, 59))) {
            time = LocalTime.of(14, 59, 59);
        }
        sumOfLength += (long) waiting.size() *
                (time.toSecondOfDay() - lastModification.toSecondOfDay());
        lastModification = time;
        return time;
    }

    /**
     * Simulates a chair or cabin arriving at the station.
     * 1. It takes up to 'capacity' athletes from the waiting queue.
     * 2. It schedules the next pickup event.
     * * @param time The current simulation time when the lift arrived.
     *
     * @return A Queue of events to be added to the main simulation priority queue.
     */
    public Queue pickUpPassengers(LocalTime time) {
        updateAverageStatics(time); // Waiting queue changes size, so update is necessary
        maximumPossibleThroughput += capacity; // Update for statistics

        Queue result = new Queue();

        // 1. Pick up athletes in FIFO order up to capacity
        for (int i = 0; i < capacity; ++i) {
            if (!waiting.isEmpty()) {
                // Retrieve the stored 'StartARide' event from the queue
                Event ev = waiting.takeFront();

                // Set the exact time the athlete actually boards the lift
                ev.setTime(time);

                // Add to the list of events that should happen now
                result.push(ev);
            } else {
                break; // No more people waiting
            }
        }

        // 2. Schedule the next chair arrival if it's still operating hours
        // The last pickup may happen at 14:59:59 or before.
        if (time.isBefore(LocalTime.of(15, 0))) {
            LocalTime nextPickupTime = time.plusSeconds(frequency);
            result.push(new PickUpPassengers(nextPickupTime, this));
        }

        return result;
    }

    /**
     * Implementation of the abstract method from Edge.
     * When an athlete chooses this lift, they are placed in the waiting line.
     */
    @Override
    public void addWaiting(Athlete athlete, LocalTime time) {
        updateAverageStatics(time); // Waiting queue changes size, so update is necessary
        /* * We wrap the athlete in a StartARide event.
         * The time is set to 'null' initially because we don't know
         * when they will finish waiting in the queue.
         */
        waiting.push(new StartARide(null, athlete, this));
    }

    public double getAverageQueueLength(LocalTime time) {
        time = updateAverageStatics(time);
        return sumOfLength / (double) (time.toSecondOfDay() - LocalTime.of(9, 0).toSecondOfDay());
    }

    public int getMaxQueueLength() {
        return waiting.getMaxLength();
    }

    public long getMaximumPossibleThroughput() {
        return maximumPossibleThroughput;
    }

    public int getUsagePercentage() {
        return (int) Math.round(
                100.0 * getServedAthletesCnt() / maximumPossibleThroughput
        );
    }

    // Athlete Event proper for Lift is Queueing up in lift
    public Event beChosenByAthlete(Athlete athlete, LocalTime time) {
        return new QueueUp(time, athlete, this);
    }

    @Override
    public String toString() {
        return "l" + getId();
    }
}