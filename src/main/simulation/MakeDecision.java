package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.*;

import java.time.LocalTime;

public class MakeDecision extends AthleteEvent {
    public MakeDecision(LocalTime time, Athlete athlete){super(time, athlete);}


    public Queue performAction(){

            // Calculate remotes lure
            int bestLift = 0;
            double bestRemoteSkiRunLure = 0;

            Node currentNode = getAthlete().getCurrentNode();
            SkiLift[] availableLifts = currentNode.getSkiLifts();
            for(int i = 0 ; i < availableLifts.length ; ++i){
                Node endDest = availableLifts[i].getEnd();
                for(SkiRun treatedRun : endDest.getSkiRuns()){
                    double runLure = getAthlete().finalRunLure(treatedRun);
                    if (runLure > bestRemoteSkiRunLure){
                        bestLift = i;
                        bestRemoteSkiRunLure = runLure;
                    }
                }
            }
            // Calculate local lure
            double bestLocalSkiRunLure = 0;
            SkiRun bestLocalSkiRun = null;
            for (SkiRun treatedRun : getAthlete().getCurrentNode().getSkiRuns()){
                double runLure = getAthlete().finalRunLure(treatedRun);
                if (runLure > bestLocalSkiRunLure){
                    bestLocalSkiRun = treatedRun;
                    bestLocalSkiRunLure = runLure;
                }
            }

            Queue result = new Queue();
            // Compare lures
            if (bestLocalSkiRunLure >= bestRemoteSkiRunLure){ // Take local run
                result.push(new TakeARun(getTime(), getAthlete(), bestLocalSkiRun));
            }
            else{ // queue up in desired lift
                getAthlete().queueUp(availableLifts[bestLift]);
            }
            return result;
    }
}
