package athlete.planner;

import resort.Edge;
import resort.Node;

import java.util.LinkedList;

public class RoutePlan {
    private LinkedList<Edge> plan;
    private Node start;

    public RoutePlan(Node start, LinkedList<Edge> plan){
        this.plan = plan;
        this.start = start;
    }

    public boolean isEmpty(){
        return plan.isEmpty();
    }

    public LinkedList<Node> getRouteNodes(){
        LinkedList<Node> result = new LinkedList<>();

        result.add(start);
        for (Edge edge : plan){
            result.add(edge.getEnd());
        }
        return result;
    }

    public int length(){
        return plan.size();
    }

    public Edge next(){
        assert ! isEmpty();
        Edge result = plan.getFirst();
        plan.removeFirst();
        return result;
    }
}
