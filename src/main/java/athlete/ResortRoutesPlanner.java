package athlete;

import resort.Edge;
import resort.Node;
import resort.SkiResort;

import java.util.Arrays;
import java.util.LinkedList;


class ResortRoutesPlanner {
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
    private LinkedList<Edge> calculateTrace(Edge[] trace, Node end){
        LinkedList<Edge> result = new LinkedList<>();

        Node curr = end;
        while (trace[curr.getId()] != null){
            result.addFirst(trace[curr.getId()]);
            curr = trace[curr.getId()].getBeginning();
        }
        return result;
    }

    RoutePlan shortestRoute(Node start, Node end){
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
                return new RoutePlan(start, calculateTrace(trace, end));
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
