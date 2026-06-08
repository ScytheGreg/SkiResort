package athlete;

import resort.Edge;

import java.util.LinkedList;

public class RoutePlan {
    private LinkedList<Edge> plan;

    public boolean isEmpty(){
        return plan.isEmpty();
    }

    public Edge next(){
        assert ! isEmpty();
        Edge result = plan.getFirst();
        plan.removeFirst();
        return result;
    }
}
