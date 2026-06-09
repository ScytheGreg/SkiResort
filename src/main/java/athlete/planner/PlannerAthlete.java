package athlete.planner;

import athlete.Athlete;
import athlete.AthleteParameters;
import randomness.UniqueGenerator;
import resort.SkiRun;
import simulation.Event;

import java.time.LocalTime;
import java.util.Random;

public abstract class PlannerAthlete extends Athlete {
    private RoutePlan plan;
    public PlannerAthlete(AthleteParameters p){
        super(p);
    }
    public abstract SkiRun chooseNextRun();

    public Event makeDecision(int seed, LocalTime time){
        if (time.isAfter(LocalTime.of(14, 59, 59))) {
            return null;
        }
        if (plan == null || plan.isEmpty()){
            /*
             * Impulsive decisions and changes of plan possible
             * only after execution of previous.
             */
            Random gen = UniqueGenerator.getGen(seed);
            if (isImpulsiveDecision(gen)){
                return makeImpulsiveDecision(gen, time);
            }

            ResortRoutesPlanner planner = ResortRoutesPlanner.getInstance(getResort());
            plan = planner.shortestRoute(getCurrentNode(), chooseNextRun());
        }
       return plan.next().beChosenByAthlete(this, time);
    }

}
