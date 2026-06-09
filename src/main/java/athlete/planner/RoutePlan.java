package athlete.planner;

import resort.Edge;
import resort.Node;

import java.util.LinkedList;

/**
 * Represents plan to be executed by PlannerAthlete
 */
public class RoutePlan {
    private final LinkedList<Edge> plan;
    private final Node start;

    public RoutePlan(Node start, LinkedList<Edge> plan) {
        this.plan = plan;
        this.start = start;
    }

    public boolean isEmpty() {
        return plan.isEmpty();
    }

    // Returns list of nodes path is coming through in respected order
    public LinkedList<Node> getRouteNodes() {
        LinkedList<Node> result = new LinkedList<>();

        result.add(start);
        for (Edge edge : plan) {
            result.add(edge.getEnd());
        }
        return result;
    }

    public int length() {
        return plan.size();
    }

    /**
     * Updates the plan after taking edge
     *
     * @return first edge from the plan
     */
    public Edge next() {
        assert !isEmpty();
        Edge result = plan.getFirst();
        plan.removeFirst();
        return result;
    }
}
