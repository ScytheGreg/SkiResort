package planner;

import org.junit.jupiter.api.BeforeEach;
import resort.*;

public class RoutesPlannerTests {

    private SkiResort resort;
    private Node[] nodes;

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
        SkiRun[] skiRuns = new SkiRun[] {
                simplRun(1, 0, 0),
                simplRun(2, 0, 1),
                simplRun(1, 2, 2),
                simplRun(3, 1, 3),
                simplRun(3, 4, 4),
                simplRun(5, 3, 5),
                simplRun(5, 3, 6)
        };

        SkiLift[] skiLifts = new SkiLift[] {
                simplLift(0, 1, 0),
                simplLift(2, 3, 1),
                simplLift(2, 4, 2),
                simplLift(4,5, 3)
        };

        resort = new SkiResort(nodes, skiLifts, skiRuns);
    }

}
