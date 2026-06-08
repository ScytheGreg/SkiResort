package athlete;

import simulation.Event;
import simulation.PickUpPassengers;

import java.time.LocalTime;

public class CollectorAthlete extends PlannerAthlete{
    public CollectorAthlete(AthleteParameters p){
        super(p);
    }
    public Event makeDecision(int seed, LocalTime time){
        return new PickUpPassengers(time, getCurrentNode().getSkiLifts()[0]);
    }
}
