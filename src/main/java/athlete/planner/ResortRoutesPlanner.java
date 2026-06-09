package athlete.planner;

import resort.Edge;
import resort.Node;
import resort.SkiResort;
import resort.SkiRun;

import java.util.Arrays;
import java.util.LinkedList;


public class ResortRoutesPlanner {
    private SkiResort resort;

    private static ResortRoutesPlanner uniquePlanner;

    private ResortRoutesPlanner(SkiResort resort){
        this.resort = resort;
    }

    public static ResortRoutesPlanner getInstance(SkiResort resort){
        if (uniquePlanner == null){
            uniquePlanner = new ResortRoutesPlanner(resort);
        }
        return uniquePlanner;
    }


    // Restores shortest path from edge trace
    private LinkedList<Edge> calculateTrace(Edge[] trace, Edge desiredRun){
        Node end = desiredRun.getBeginning();
        LinkedList<Edge> result = new LinkedList<>();
        result.addLast(desiredRun);

        Node curr = end;
        while (trace[curr.getId()] != null){
            result.addFirst(trace[curr.getId()]);
            curr = trace[curr.getId()].getBeginning();
        }
        return result;
    }

    public RoutePlan shortestRoute(Node start, Edge desiredRun){
        Node end = desiredRun.getBeginning();
        boolean[] visited = new boolean[resort.getNodesNum()];
        Arrays.fill(visited, false);

        LinkedList<Node> BFSQueue = new LinkedList<>();
        BFSQueue.addLast(start);

        // Saves for every Node edge that we came in
        Edge[] trace = new Edge[resort.getNodesNum()];
        Arrays.fill(trace, null);

        while(!BFSQueue.isEmpty()){
            Node curr = BFSQueue.getFirst();
            BFSQueue.removeFirst();
            visited[curr.getId()] = true;

            if (curr.equals(end)){
                return new RoutePlan(start, calculateTrace(trace, desiredRun));
            }

            for (Edge edge : curr.getEdges()){
                if (! visited[edge.getEnd().getId()]){
                    trace[edge.getEnd().getId()] = edge;
                    BFSQueue.addLast(edge.getEnd());
                }
            }
        }

        throw new RuntimeException("BFS has not reached end node");
    }
}
