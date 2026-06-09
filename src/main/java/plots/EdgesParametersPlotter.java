package plots;

import resort.SkiLift;
import resort.SkiRun;

import java.util.LinkedList;

public class EdgesParametersPlotter extends Plotter{

    protected LinkedList<String> liftLabel(SkiLift lift){
        LinkedList<String> result =  new LinkedList<>();
        result.add(lift + ": " + lift.getCapacity()  + " people every "
                    + lift.getFrequency() + "s");
        result.add("time: " + lift.getRideTime());

        return result;
    }

    protected LinkedList<String> runLabel(SkiRun run){
        LinkedList<String> result =  new LinkedList<>();
        result.add(run + ": level: " + run.getAdvanceLevel()
        +", time: " + run.getRideTime() + "s");
        result.add("toughness: " + run.getBaseLure()+ ", " + run.getToughness());
        return result;
    }
}
