package athlete;

import randomness.UniqueGenerator;
import resort.Edge;
import resort.SkiRun;
import simulation.Event;
import simulation.PickUpPassengers;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Random;

public abstract class PlannerAthlete extends Athlete{
    private RoutePlan plan;
    public PlannerAthlete(AthleteParameters p){
        super(p);
    }
    public abstract SkiRun chooseNextRun();

    public Event makeDecision(int seed, LocalTime time){
        if (plan.isEmpty()){
            /*
             * Impulsive decisions and changes of plan possible
             * only after execution of previous.
             */
            Random gen = UniqueGenerator.getGen(seed);
            if (isImpulsiveDecision(gen)){
                return makeImpulsiveDecision(gen, time);
            }

            ResortRoutesPlanner planner = ResortRoutesPlanner.getInstance(getResort());
            plan = planner.shortestRoute(getCurrentNode(), chooseNextRun().getBeginning());
        }
       return plan.next().beChoosenByAthlete(this, time);
    }

}
