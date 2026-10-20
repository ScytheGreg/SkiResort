package athlete.planner;

import athlete.AthleteParameters;
import resort.SkiRun;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Tries to ride with every run a similar number of times.
 */
public class CollectorAthlete extends PlannerAthlete {
    int[] runsCnt;  // Counters for every run - number of taken rides
    int minRunsCnt; // Keeps minimum of array runsCnt

    public CollectorAthlete(AthleteParameters p) {
        super(p);
        runsCnt = new int[getResort().getSkiRunsNum()];
        Arrays.fill(runsCnt, 0);
        minRunsCnt = 0;
    }


    /*
     * Logic:
     * 1. try to level out runsCnt table
     * 2. if equal, choose nearest
     * 3. if equal, choose most attractive
     */
    public SkiRun chooseNextRun() {

        // Level out collection of runs
        ArrayList<SkiRun> collectionCandidates = new ArrayList<>();
        for (SkiRun run : getResort().getSkiRuns()) {
            if (runsCnt[run.getId()] == minRunsCnt) {
                collectionCandidates.add(run);
            }
        }

        /* If every SkiRun has been visited same positive cnt
         * candidates array is empty. minRunsCnt requires upgrade
         * to prevent looping.
         */
        if (collectionCandidates.isEmpty()) {
            minRunsCnt++;
            return chooseNextRun();
        }

        // From previous criterion candidate choose those nearest
        ResortRoutesPlanner planner = ResortRoutesPlanner.getInstance(getResort());
        int shortestDistance = Integer.MAX_VALUE;
        ArrayList<SkiRun> distanceCandidates = new ArrayList<>();
        for (SkiRun run : collectionCandidates) {
            int distance = planner.shortestRoute(getCurrentNode(), run).length();
            if (distance < shortestDistance) {
                distanceCandidates.clear();     // Remove runs with bigger distance;
                distanceCandidates.add(run);
                shortestDistance = distance;
            } else if (distance == shortestDistance) {
                distanceCandidates.add(run);
            }
        }

        // From previous criterion candidates choose those most attractive
        double bestLure = Double.MIN_VALUE;
        SkiRun bestLureCandidate = null;
        for (SkiRun run : distanceCandidates) {
            if (finalRunLure(run) > bestLure) {
                bestLure = finalRunLure(run);
                bestLureCandidate = run;
            }
        }
        ++runsCnt[bestLureCandidate.getId()]; // Finally update counter
        return bestLureCandidate;             // And return
    }
}
