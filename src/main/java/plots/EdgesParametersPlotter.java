package plots;

import resort.SkiLift;
import resort.SkiRun;

import java.util.LinkedList;

/**
 * Plots parameters of ski runs and lifts
 * in accordance with assignment requirements
 */
public class EdgesParametersPlotter extends Plotter {

    protected LinkedList<String> liftLabel(SkiLift lift) {
        LinkedList<String> result = new LinkedList<>();
        result.add(lift + ": " + lift.getCapacity() + " people every "
                + lift.getFrequency() + "s");
        result.add("time: " + lift.getRideTime());

        return result;
    }

    protected LinkedList<String> runLabel(SkiRun run) {
        LinkedList<String> result = new LinkedList<>();
        result.add(run + ": level: " + run.getAdvanceLevel()
                + ", time: " + run.getRideTime() + "s");
        result.add("toughness: " + String.format("%.2f", run.getBaseLure())
                + ", " + String.format("%.5f", run.getToughness()));
        return result;
    }
}
