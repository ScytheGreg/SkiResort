package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.*;

import java.time.LocalTime;
import java.util.Random;

public class MakeDecision extends AthleteEvent {
    //Constructor
    public MakeDecision(LocalTime time, Athlete athlete) {
        super(time, athlete);
    }

    private boolean isImpulsiveDecision(Random generator) {
        if (generator.nextDouble() <= getAthlete().getImpulsivity())
            return true;
        return false;
    }

    private Event makeImpulsiveDecision(Random generator){
        Athlete athlete = getAthlete();
        Node currentNode = athlete.getCurrentNode();
        SkiRun[] skiRuns = currentNode.getSkiRuns();
        SkiLift[] skiLifts = currentNode.getSkiLifts();

        int n = skiRuns.length + skiLifts.length;

        int takenEdge = generator.nextInt(0, n);
        if (takenEdge < skiRuns.length){
            return new TakeARun(getTime(), athlete, skiRuns[takenEdge]);
        }
        athlete.queueUp(skiLifts[takenEdge - skiRuns.length]);
        return null;
    }

    public Queue performAction(int seed) {
        Queue result = new Queue();
        Random generator = new Random(getTime().toSecondOfDay() + seed);

        if (isImpulsiveDecision(generator)) {
            result.push(makeImpulsiveDecision(generator));
            return result;
        }

        // Calculate remotes lure
        int bestLift = 0;
        double bestRemoteSkiRunLure = 0;

        Node currentNode = getAthlete().getCurrentNode();
        SkiLift[] availableLifts = currentNode.getSkiLifts();
        for (int i = 0; i < availableLifts.length; ++i) {
            Node endDest = availableLifts[i].getEnd();
            for (SkiRun treatedRun : endDest.getSkiRuns()) {
                double runLure = getAthlete().finalRunLure(treatedRun);
                if (runLure > bestRemoteSkiRunLure) {
                    bestLift = i;
                    bestRemoteSkiRunLure = runLure;
                }
            }
        }
        // Calculate local lure
        double bestLocalSkiRunLure = 0;
        SkiRun bestLocalSkiRun = null;
        for (SkiRun treatedRun : getAthlete().getCurrentNode().getSkiRuns()) {
            double runLure = getAthlete().finalRunLure(treatedRun);
            if (runLure > bestLocalSkiRunLure) {
                bestLocalSkiRun = treatedRun;
                bestLocalSkiRunLure = runLure;
            }
        }

        // Compare lures
        if (bestLocalSkiRunLure >= bestRemoteSkiRunLure) { // Take local run
            result.push(new TakeARun(getTime(), getAthlete(), bestLocalSkiRun));
        } else { // queue up in desired lift
            getAthlete().queueUp(availableLifts[bestLift]);
        }
        return result;
    }

    @Override
    public String toString(){
        return super.toString() + " is making a decision.";
    }
}
