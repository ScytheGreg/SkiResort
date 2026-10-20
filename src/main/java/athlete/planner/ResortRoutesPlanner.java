package athlete.planner;

import resort.Edge;
import resort.Node;
import resort.SkiResort;

import java.util.Arrays;
import java.util.LinkedList;

/*
 * Maintains searching for shortest possible route
 * (path in resort graph) from starting point to beginning
 * of desired edge.
 * SINGULARITY
 */

public class ResortRoutesPlanner {
    private final SkiResort resort;
    private static ResortRoutesPlanner uniquePlanner;

    private ResortRoutesPlanner(SkiResort resort) {
        this.resort = resort;
    }

    public static ResortRoutesPlanner getInstance(SkiResort resort) {
        if (uniquePlanner == null) {
            uniquePlanner = new ResortRoutesPlanner(resort);
        }
        return uniquePlanner;
    }


    /**
     * Restores shortest path from edge trace
     *
     * @param trace      - every node saved there incoming edge in restored path
     * @param desiredRun - run athlete wanted to take. Is place at the end of result
     * @return list of edges that plan require to take
     */
    private LinkedList<Edge> restorePathFromTrace(Edge[] trace, Edge desiredRun) {
        Node end = desiredRun.getBeginning();
        LinkedList<Edge> result = new LinkedList<>();
        result.addLast(desiredRun);

        Node curr = end;
        while (trace[curr.getId()] != null) {
            result.addFirst(trace[curr.getId()]);
            curr = trace[curr.getId()].getBeginning();
        }
        return result;
    }

    /**
     * Using BFS algorithm searches resort graph to find the shortest
     * route to beginning of desired path
     *
     * @param start      - node from athlete want to start
     * @param desiredRun - run chosen by athlete
     * @return ready plan for athlete to execute
     */
    public RoutePlan shortestRoute(Node start, Edge desiredRun) {
        Node end = desiredRun.getBeginning();                  // BFS target
        boolean[] visited = new boolean[resort.getNodesNum()]; // BFS visited array
        Arrays.fill(visited, false);

        LinkedList<Node> BFSQueue = new LinkedList<>();        // BFS queue
        BFSQueue.addLast(start);

        // Saves for every node an edge with witch path has came into that node
        Edge[] trace = new Edge[resort.getNodesNum()];
        Arrays.fill(trace, null);

        while (!BFSQueue.isEmpty()) {
            // Queue front handling
            Node curr = BFSQueue.getFirst();
            BFSQueue.removeFirst();
            visited[curr.getId()] = true;

            // Check end condition, restore path from edges trace
            if (curr.equals(end)) {
                return new RoutePlan(start, restorePathFromTrace(trace, desiredRun));
            }

            // BFS updates the queue
            for (Edge edge : curr.getEdges()) {
                if (!visited[edge.getEnd().getId()]) {
                    trace[edge.getEnd().getId()] = edge;
                    BFSQueue.addLast(edge.getEnd());
                }
            }
        }
        // End condition should always be reached
        // , because resort graph is strongly connected
        throw new RuntimeException("BFS has not reached end node");
    }
}
