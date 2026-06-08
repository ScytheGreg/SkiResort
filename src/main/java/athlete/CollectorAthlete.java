package athlete;

import resort.SkiRun;
import simulation.Event;
import simulation.PickUpPassengers;

import java.time.LocalTime;

public class CollectorAthlete extends PlannerAthlete{
    public CollectorAthlete(AthleteParameters p){
        super(p);
    }
    public SkiRun chooseNextRun(){
        return getCurrentNode().getSkiRuns()[0];
    }
}
