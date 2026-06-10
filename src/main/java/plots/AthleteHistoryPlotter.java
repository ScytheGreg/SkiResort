package plots;

import athlete.observer.HistoryObserver;
import resort.Edge;
import resort.SkiLift;
import resort.SkiRun;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Plots history of rides of one traced athlete
 * in accordance with assignment requirements
 */
public class AthleteHistoryPlotter extends Plotter {
    private final HistoryObserver observer;

    /**
     * @param observer instead of Athlete to avoid plotting not traced athletes
     */
    public AthleteHistoryPlotter(HistoryObserver observer) {
        this.observer = observer;
    }

    private LinkedList<String> edgeLabel(Edge edge) {
        // Take history of athlete from edge
        ArrayList<Integer> history = edge.getHistory(observer);
        String label = "";
        if (history != null) {
            label = edge + "(" + history.size() + "): ";
            for (int moveId : history) {
                label += moveId;
                label += ",";
            }
        }
        LinkedList<String> result = new LinkedList<>();
        result.add(label);
        return result;
    }

    protected LinkedList<String> liftLabel(SkiLift lift) {
        return edgeLabel(lift);
    }

    protected LinkedList<String> runLabel(SkiRun run) {
        return edgeLabel(run);
    }
}
