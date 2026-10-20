package athlete.planner;

import athlete.Athlete;
import athlete.AthleteParameters;
import randomness.UniqueGenerator;
import resort.Edge;
import resort.SkiRun;
import simulation.Event;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Random;

/*
 * Planner Athlete makes decision based on his plan.
 *  If plan has been realized, sets new plan with algorithm:
 *  -> choose deserved run
 *  -> get to the beginning of that run shortest possible way
 *  -> take that run
 */

public abstract class PlannerAthlete extends Athlete {
    private RoutePlan plan; // Plan that must be followed

    public PlannerAthlete(AthleteParameters p) {
        super(p);
    }

    // There athlete sets his preferences
    public abstract SkiRun chooseNextRun();

    /**
     * Core decision logic for the athlete.
     * Follows plan. Sets new if empty.
     */
    public Event makeDecision(int seed, LocalTime time) {
        // Stop makingDecisions after 15:00:00
        if (time.isAfter(LocalTime.of(14, 59, 59))) {
            return null;
        }

        if (plan == null || plan.isEmpty()) {
            /*
             * Impulsive decisions and changes of plan possible
             * only after execution of previous.
             * Decide if performing impulsive decision.
             */
            Random gen = UniqueGenerator.getGen(seed);
            if (isImpulsiveDecision(gen)) {
                return makeImpulsiveDecision(gen, time);
            }

            // Make a new plan
            ResortRoutesPlanner planner = ResortRoutesPlanner.getInstance(getResort());
            plan = planner.shortestRoute(getCurrentNode(), chooseNextRun());
        }

        // Follow plan
        return plan.next().beChosenByAthlete(this, time);
    }

}
