package athlete;

import containers.Queue;
import resort.SkiRun;
import simulation.Event;
import simulation.PickUpPassengers;
import simulation.QueueUp;

import java.time.LocalTime;

public class GreedyAthlete extends PlannerAthlete{
    public GreedyAthlete(AthleteParameters p){
        super(p);
    }

    public SkiRun chooseNextRun(){
        double bestSkiRunLure = Double.MIN_VALUE;
        SkiRun bestSkiRun = null;
        for (SkiRun skiRun : getResort().getSkiRuns()){
            if (finalRunLure(skiRun) > bestSkiRunLure){
                bestSkiRun = skiRun;
                bestSkiRunLure = finalRunLure(skiRun);
            }
        }
        return bestSkiRun;
    }

}
