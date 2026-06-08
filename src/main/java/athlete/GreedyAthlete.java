package athlete;

import containers.Queue;
import simulation.Event;
import simulation.PickUpPassengers;
import simulation.QueueUp;

import java.time.LocalTime;

public class GreedyAthlete extends PlannerAthlete{
    public GreedyAthlete(AthleteParameters p){
        super(p);
    }
    public Event makeDecision(int seed, LocalTime time){
        return new PickUpPassengers(time, getCurrentNode().getSkiLifts()[0]);
    }

}
