package planner;

import athlete.planner.ResortRoutesPlanner;
import athlete.planner.RoutePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resort.*;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit tests required in assignment specification
 */
public class RoutesPlannerTests {

    private SkiResort resort;
    private Node[] nodes;
    private SkiRun[] skiRuns;
    private SkiLift[] skiLifts;
    private ResortRoutesPlanner planner;

    private SkiRun simplRun(int beg, int end, int id) {
        return new SkiRun(nodes[beg], nodes[end], 1, id, 1, 1, 1);
    }

    private SkiLift simplLift(int beg, int end, int id) {
        return new SkiLift(nodes[beg], nodes[end], 1, id, 1, 1);
    }


    @BeforeEach
    void setUp() {
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, 0, 0, 0, true);
        }
        skiRuns = new SkiRun[]{
                simplRun(1, 0, 0),
                simplRun(2, 0, 1),
                simplRun(1, 2, 2),
                simplRun(3, 1, 3),
                simplRun(3, 4, 4),
                simplRun(5, 3, 5),
                simplRun(5, 3, 6)
        };

        skiLifts = new SkiLift[]{
                simplLift(0, 1, 0),
                simplLift(2, 3, 1),
                simplLift(2, 4, 2),
                simplLift(4, 5, 3)
        };

        resort = new SkiResort(nodes, skiLifts, skiRuns);
        planner = ResortRoutesPlanner.getInstance(resort);
    }

    private void checkPath(RoutePlan plan, int expLength, int[] expNodes){
        assertEquals(expLength, plan.length());
        LinkedList<Node> stops = plan.getRouteNodes();

        int i;
        for (i = 0 ; i < expNodes.length ; ++i){
            assertEquals(expNodes[i], stops.get(i).getId());
        }
        final int k = i + 1;
        assertThrows(IndexOutOfBoundsException.class, () -> stops.get(k));

    }

    @Test
    void shouldFindCorrectShortestPath() {
        RoutePlan plan = planner.shortestRoute(nodes[0], skiLifts[3]); // Lift (4, 5)
        checkPath(plan, 4, new int[]{0, 1, 2, 4, 5});
    }

    @Test
    void shouldFindDirectRoute() {
        RoutePlan plan = planner.shortestRoute(nodes[3], skiRuns[0]); // Run (1, 0)
        checkPath(plan, 2, new int[]{3, 1, 0});
    }

    @Test
    void shouldFindEmptyPath() {
        RoutePlan plan = planner.shortestRoute(nodes[2], skiRuns[1]); // Run (2, 0)
        checkPath(plan, 1, new int[] {2, 0});
    }

    @Test
    void shouldChooseWhenAmb() {
        RoutePlan plan = planner.shortestRoute(nodes[4], skiRuns[3]); // Run (3, 1)
        checkPath(plan, 3, new int[] {4, 5, 3});
    }
}
