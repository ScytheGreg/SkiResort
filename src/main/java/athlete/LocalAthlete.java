package athlete;

import randomness.UniqueGenerator;
import resort.Node;
import resort.SkiLift;
import resort.SkiRun;
import simulation.Event;

import java.time.LocalTime;
import java.util.Random;

public class LocalAthlete extends Athlete{

    public LocalAthlete(AthleteParameters p){
        super(p);
    }

    /**
     * Core decision logic for the athlete.
     * Considers local runs and runs accessible via one lift to find the best option.
     */
    public Event makeDecision(int seed, LocalTime time) {
        // Stop making new decisions after 15:00:00[cite: 45, 134].
        if (time.isAfter(LocalTime.of(14, 59, 59))) {
            return null;
        }

        // Draw unique generator
        Random generator = UniqueGenerator.getGen(seed);

        if (isImpulsiveDecision(generator)) {
            return makeImpulsiveDecision(generator, time);
        }

        // 1. Evaluate "Remote" runs: those accessible by taking a lift from here
        int bestLiftIndex = 0;
        double bestRemoteSkiRunLure = 0;

        Node current = getCurrentNode();
        SkiLift[] availableLifts = current.getSkiLifts();

        for (int i = 0; i < availableLifts.length; ++i) {
            Node topStation = availableLifts[i].getEnd();
            for (SkiRun potentialRun : topStation.getSkiRuns()) {
                double runLure = finalRunLure(potentialRun);
                if (runLure > bestRemoteSkiRunLure) {
                    bestLiftIndex = i;
                    bestRemoteSkiRunLure = runLure;
                }
            }
        }

        // 2. Evaluate "Local" runs: those starting directly at the current node
        double bestLocalSkiRunLure = 0;
        SkiRun bestLocalSkiRun = null;

        for (SkiRun treatedRun : current.getSkiRuns()) {
            double runLure = finalRunLure(treatedRun);
            if (runLure > bestLocalSkiRunLure) {
                bestLocalSkiRun = treatedRun;
                bestLocalSkiRunLure = runLure;
            }
        }

        // 3. Final Comparison: Pick the most attractive option [cite: 103]
        if (bestLocalSkiRunLure >= bestRemoteSkiRunLure && bestLocalSkiRun != null) {
            // Start skiing immediately if a local run is best [cite: 104]
            return bestLocalSkiRun.beChosenByAthlete(this, time);
        } else if (availableLifts.length > 0) {
            // Queue up for a lift if a remote run is more attractive [cite: 105]
            return availableLifts[bestLiftIndex].beChosenByAthlete(this, time);
        }

        return null;
    }
}
