package athlete;

import resort.SkiRun;

import java.util.HashMap;
import java.util.Map;

/*
 * Responsible for boredom of ski runs management.
 */

public class AthletesMemoryOfRuns {
    private int takenRunsCnt = 0;       // Counter of taken runs
    private final Athlete athlete;

    // Keeps data for every visited ski run
    private final Map<SkiRun, RunMemoryData> memory = new HashMap<>();

    public AthletesMemoryOfRuns(Athlete athlete) {
        this.athlete = athlete;
    }


    // With every memory update ski run creates new RunMemoryDataClass
    private class RunMemoryData {
        private final int lastRideTime;       // (in taken runs from start)
        private final double lastRideBoredom; // Boredom of last ride by this run

        public RunMemoryData(SkiRun run) {
            this.lastRideTime = takenRunsCnt;
            // Calculate boredom from formula:
            // z_t = (1 - beta) * z_{t - 1} + beta (*1)
            this.lastRideBoredom = calculateRunBoredom(run) + athlete.getBoredomRate();
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
    public double calculateRunBoredom(SkiRun run) {
        if (memory.containsKey(run)) {
            RunMemoryData memberedData = memory.get(run);
            return memberedData.getLastRideBoredom() *
                    Math.pow(1 - athlete.getBoredomRate(),
                            takenRunsCnt - memberedData.getLastRideTime());
        }
        return 0; // Case run has not been visited by this athlete
    }

    // Updates memory - replaces old memory token with new one
    // associated to that run
    public void takeARun(SkiRun run) {
        ++takenRunsCnt;
        if (memory.containsKey(run)) {
            // Replace based on previous data
            memory.replace(run, new RunMemoryData(run));
        } else {
            memory.put(run, new RunMemoryData(run));
        }
    }
}
