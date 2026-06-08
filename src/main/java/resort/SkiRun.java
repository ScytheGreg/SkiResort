package resort;

import athlete.Athlete;
import simulation.Event;
import simulation.StartARide;

import java.time.LocalTime;

/**
 * Represents a downhill ski run (trasa zjazdowa).
 * Unlike lifts, ski runs do not have queues; athletes simply start the ride.
 * The quality of the run's surface decreases as more athletes use it.
 */
public class SkiRun extends Edge {
    private final int advanceLevel; // Required skill level (0-10)
    private final double baseLure;  // w_base: the minimum attractiveness of the surface
    private final double toughness; // t: how resistant the run is to wearing out (0 to 1)

    public SkiRun(Node beginning,
                  Node end,
                  int rideTime,
                  int id,
                  int advanceLevel,
                  double baseLure,
                  double toughness) {

        super(beginning, end, rideTime, id);

        this.advanceLevel = advanceLevel;
        this.baseLure = baseLure;
        this.toughness = toughness;
    }

    @Override
    public String getName() {
        return "ski run";
    }

    public int getAdvanceLevel() {
        return advanceLevel;
    }

    /**
     * Calculates the current surface quality attractiveness $w(n)$.
     * * Formula: $w(n) = w_{base} + (1 - w_{base}) \cdot t^n$
     * where:
     * - $w_{base}$ is the baseLure
     * - $t$ is the toughness
     * - $n$ is the number of athletes who have used this run so far
     * * As $n$ increases, the value moves from 1.0 down towards $w_{base}$.
     */
    public double smoothnessCalculate() {
        // getServedAthletesCnt() comes from the parent Edge class
        return baseLure + (1 - baseLure) * Math.pow(toughness, getServedAthletesCnt());
    }

    /**
     * Implementation of the abstract method from Edge.
     * * For a SkiRun, there is no physical queue or waiting mechanism.
     * When an athlete "waits" for a run, they effectively start it immediately
     * in the simulation logic, so this method remains empty.
     */
    @Override
    public void addWaiting(Athlete athlete) {
        // No queueing logic needed for downhill runs.
    }

    public Event beChoosenByAthlete(Athlete athlete, LocalTime time) {
        return new StartARide(time, athlete, this);
    }
}