package athlete;

import resort.SkiRun;

import java.util.HashMap;
import java.util.Map;

public class MemoryOfRuns {
    private int takenRunsCnt = 0;       // Counter of taken runs
    private final Map<SkiRun, RunMemoryData> memory = new HashMap<>(); // S
    private final Athlete athlete;

    public MemoryOfRuns(Athlete athlete) {
        this.athlete = athlete;
    }

    private class RunMemoryData {
        private int lastRideTime;       // Time (in taken runs from start)
        // of last ride
        private double lastRideBoredom; // Boredom of last ride by this run

        public RunMemoryData(SkiRun run) {
            this.lastRideTime = takenRunsCnt;
            // Calculate boredom from formula:
            // z_t = (1 - beta) * z_{t - 1} + beta
            this.lastRideBoredom = calculateRouteBoredom(run) + athlete.getBoredomRate();
        }

        public int getLastRideTime() {
            return lastRideTime;
        }

        public double getLastRideBoredom() {
            return lastRideBoredom;
        }

    }

    // Calculates boredom by formula:
    // z_k \cdot (1 - \beta) ^ i. Where:
    // z_k - last ride by this route boredom
    // beta - boredomRate of athlete
    // i - time passed from last run = takenRunsCnt - lastRideTime
    public double calculateRouteBoredom(SkiRun run) {
        if (memory.containsKey(run)) {
            RunMemoryData memberedData = memory.get(run);
            return memberedData.getLastRideBoredom() *
                    Math.pow(1 - athlete.getBoredomRate(),
                            takenRunsCnt - memberedData.getLastRideTime());
        }
        return 0;
    }

    // Updates memory
    public void takeARun(SkiRun run) {
        ++takenRunsCnt;
        if (memory.containsKey(run)) {
            memory.replace(run, new RunMemoryData(run));
        } else {
            memory.put(run, new RunMemoryData(run));
        }
    }
}
