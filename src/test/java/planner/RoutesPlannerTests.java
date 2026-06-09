package planner;

import athlete.planner.ResortRoutesPlanner;
import athlete.planner.RoutePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resort.*;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoutesPlannerTests {

    private SkiResort resort;
    private Node[] nodes;
    private SkiRun[] skiRuns;
    private SkiLift[] skiLifts;
    private ResortRoutesPlanner planner;

    private SkiRun simplRun(int beg, int end, int id){
        return new SkiRun(nodes[beg], nodes[end], 1, id, 1, 1, 1);
    }

    private SkiLift simplLift(int beg, int end, int id){
        return new SkiLift(nodes[beg], nodes[end], 1, id, 1, 1);
    }


    @BeforeEach
    void setUp(){
        nodes = new Node[6];
        for (int i  = 0 ; i < nodes.length ; ++i){
            nodes[i] = new Node(i, 0, 0, 0, true);
        }
        skiRuns = new SkiRun[] {
                simplRun(1, 0, 0),
                simplRun(2, 0, 1),
                simplRun(1, 2, 2),
                simplRun(3, 1, 3),
                simplRun(3, 4, 4),
                simplRun(5, 3, 5),
                simplRun(5, 3, 6)
        };

        skiLifts = new SkiLift[] {
                simplLift(0, 1, 0),
                simplLift(2, 3, 1),
                simplLift(2, 4, 2),
                simplLift(4, 5, 3)
        };

        resort = new SkiResort(nodes, skiLifts, skiRuns);
        planner = ResortRoutesPlanner.getInstance(resort);
    }

    @Test
    void shouldFindCorrectShortestPath(){
        RoutePlan plan = planner.shortestRoute(nodes[0], skiLifts[3]);

        assertEquals(4, plan.length()); // 3 + desiredEdge
        LinkedList<Node> stops = plan.getRouteNodes();

        assertEquals(0, stops.get(0).getId());
        assertEquals(1, stops.get(1).getId());
        assertEquals(2, stops.get(2).getId());
        assertEquals(4, stops.get(3).getId());
        assertEquals(5, stops.get(4).getId());
        assertThrows(IndexOutOfBoundsException.class, () -> stops.get(5));
    }

    @Test
    void shouldFindDirectRoute(){
        RoutePlan plan = planner.shortestRoute(nodes[3], skiRuns[0]);
        assertEquals(2, plan.length());

        LinkedList<Node> stops = plan.getRouteNodes();
        assertEquals(3, stops.get(0).getId());
        assertEquals(1, stops.get(1).getId());
        assertEquals(0, stops.get(2).getId());
    }

    @Test
    void shouldFindEmptyPath(){
        RoutePlan plan = planner.shortestRoute(nodes[2], skiRuns[1]);
        assertEquals(1, plan.length());

        LinkedList<Node> stops = plan.getRouteNodes();
        assertEquals(2, stops.get(0).getId());
        assertEquals(0, stops.get(1).getId());
    }

    @Test
    void shouldChooseWhenAmb(){
        RoutePlan plan = planner.shortestRoute(nodes[4], skiRuns[3]);
        assertEquals(3, plan.length());

        LinkedList<Node> stops = plan.getRouteNodes();
        assertEquals(4, stops.get(0).getId());
        assertEquals(5, stops.get(1).getId());
        assertEquals(3, stops.get(2).getId());
    }
}
