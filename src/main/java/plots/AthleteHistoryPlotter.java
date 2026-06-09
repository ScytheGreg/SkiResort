package plots;

import athlete.Athlete;
import athlete.observer.HistoryObserver;
import resort.Edge;
import resort.SkiLift;
import resort.SkiRun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class AthleteHistoryPlotter {
    private final Athlete athlete;

    public AthleteHistoryPlotter(HistoryObserver observer){
        this.athlete = observer.getAthlete();
    }

    private LinkedList<String> edgeLabel(Edge edge){
        ArrayList<Integer> history = edge.getHistory(athlete);
        String label = edge + "(" + history.size() + "): ";
        for (int moveId : history){
            label += moveId;
            label += ",";
        }
        LinkedList<String> result =  new LinkedList<>();
        result.add(label);
        return result;
    }

    protected LinkedList<String> liftLabel(SkiLift lift){
        return edgeLabel(lift);
    }

    protected LinkedList<String> runLabel(SkiRun run){
        return edgeLabel(run);
    }
}
