package athlete;

import resort.Edge;
import resort.SkiRun;
import simulation.Event;
import simulation.PickUpPassengers;

import java.time.LocalTime;
import java.util.LinkedList;

public abstract class PlannerAthlete extends Athlete{
    private RoutePlan plan = new RoutePlan();
    public PlannerAthlete(AthleteParameters p){
        super(p);
    }
    public abstract SkiRun chooseNextRun();

    public Event makeDecision(int seed, LocalTime time){
        if (plan.isEmpty()){
            plan = ResortRoutesPlanner.shortestRoute(getCurrentNode(), chooseNextRun().getBeginning());
        }
       return plan.next().beChoosenByAthlete(this, time);
    }

}
