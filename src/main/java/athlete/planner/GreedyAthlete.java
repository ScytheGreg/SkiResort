package athlete.planner;

import athlete.AthleteParameters;
import resort.SkiRun;


/*
 * Searches for the most attractive run in the whole resort.
 */
public class GreedyAthlete extends PlannerAthlete {
    public GreedyAthlete(AthleteParameters p) {
        super(p);
    }

    /*
     * @return bestSkiRun - most attractive run in the whole resort
     */
    public SkiRun chooseNextRun() {
        double bestSkiRunLure = Double.MIN_VALUE;
        SkiRun bestSkiRun = null;
        for (SkiRun skiRun : getResort().getSkiRuns()) {
            if (finalRunLure(skiRun) > bestSkiRunLure) {
                bestSkiRun = skiRun;
                bestSkiRunLure = finalRunLure(skiRun);
            }
        }
        return bestSkiRun;
    }

}
