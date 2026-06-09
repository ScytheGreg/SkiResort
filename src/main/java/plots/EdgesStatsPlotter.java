package plots;

import resort.SkiLift;
import resort.SkiRun;

import java.time.LocalTime;
import java.util.LinkedList;

public class EdgesStatsPlotter extends Plotter{

    protected LinkedList<String> liftLabel(SkiLift lift){
        LinkedList<String> result =  new LinkedList<>();
        result.add(lift + ": que: " + lift.getAverageQueueLength(LocalTime.of(15,0))
                + "(av), " + lift.getMaxQueueLength() + "(max)");
                result.add("usage: " + lift.getServedAthletesCnt() + " / " +
                lift.getMaximumPossibleThroughput() +
                "(" + lift.getUsage() + "%)");

        return result;
    }

    protected LinkedList<String> runLabel(SkiRun run){
        LinkedList<String> result =  new LinkedList<>();
        result.add(run + ": snow: " + run.smoothnessCalculate());
        result.add("runs:" + run.getServedAthletesCnt());
        return result;
    }
}
