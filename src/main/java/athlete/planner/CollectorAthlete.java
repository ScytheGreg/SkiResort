package athlete.planner;

import athlete.AthleteParameters;
import resort.SkiRun;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectorAthlete extends PlannerAthlete {
    int[] runsCnt;
    int minRunsCnt; // Keeps minimum of array runsCnt

    public CollectorAthlete(AthleteParameters p){
        super(p);
        runsCnt = new int[getResort().getSkiRunsNum()];
        Arrays.fill(runsCnt, 0);
        minRunsCnt = 0;
    }

    public SkiRun chooseNextRun(){

        ArrayList<SkiRun> collectionCandidates = new ArrayList<>();
        for (SkiRun run : getResort().getSkiRuns()){
            if (runsCnt[run.getId()] == minRunsCnt){
                collectionCandidates.add(run);
            }
        }

        // Case every SkiRun has been visited same positive cnt
        if (collectionCandidates.isEmpty()){
            minRunsCnt++;
            return chooseNextRun();
        }

        ResortRoutesPlanner planner = ResortRoutesPlanner.getInstance(getResort());
        int shortestDistance = Integer.MAX_VALUE;
        ArrayList<SkiRun> distanceCandidates = new ArrayList<>();
        for (SkiRun run : collectionCandidates){
            int distance = planner.shortestRoute(getCurrentNode(), run).length();
            if (distance < shortestDistance){
                distanceCandidates.clear();     // Remove runs with bigger distance;
                distanceCandidates.add(run);
                shortestDistance = distance;
            } else if (distance == shortestDistance) {
                distanceCandidates.add(run);
            }
        }

        double bestLure = Double.MIN_VALUE;
        SkiRun bestLureCandidate = null;
        for (SkiRun run : distanceCandidates){
            if (finalRunLure(run) > bestLure){
                bestLure = finalRunLure(run);
                bestLureCandidate = run;
            }
        }

        return bestLureCandidate;
    }
}
