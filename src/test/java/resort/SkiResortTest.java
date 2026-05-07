package resort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkiResortTest {

    @Test
    void testConstructorAndBasicGetters() {
        Node[] nodes = new Node[]{
                new Node(0, 1000, 0, 0, true),
                new Node(1, 1200, 1, 1, false)
        };

        SkiLift[] lifts = new SkiLift[0];
        SkiRun[] runs = new SkiRun[0];

        SkiResort resort = new SkiResort(
                nodes,
                nodes.length,
                lifts,
                lifts.length,
                runs,
                runs.length
        );

        assertSame(nodes, resort.getNodes());
        assertSame(lifts, resort.getSkiLifts());
        assertSame(runs, resort.getSkiRuns());

        assertEquals(2, resort.getNodesNum());
        assertEquals(0, resort.getSkiLiftsNum());
        assertEquals(0, resort.getSkiRunsNum());
    }

    @Test
    void testConnectSingleLiftToNode() {
        Node n0 = new Node(0, 1000, 0, 0, true);
        Node n1 = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                n0,
                n1,
                120,
                0,
                10,
                4
        );

        SkiResort resort = new SkiResort(
                new Node[]{n0, n1},
                2,
                new SkiLift[]{lift},
                1,
                new SkiRun[0],
                0
        );

        assertEquals(1, n0.getSkiLifts().length);
        assertSame(lift, n0.getSkiLifts()[0]);

        assertEquals(0, n1.getSkiLifts().length);
    }

    @Test
    void testConnectSingleRunToNode() {
        Node n0 = new Node(0, 1200, 0, 0, true);
        Node n1 = new Node(1, 900, 1, 1, false);

        SkiRun run = new SkiRun(
                n0,
                n1,
                90,
                0,
                5,
                0.3,
                0.99
        );

        SkiResort resort = new SkiResort(
                new Node[]{n0, n1},
                2,
                new SkiLift[0],
                0,
                new SkiRun[]{run},
                1
        );

        assertEquals(1, n0.getSkiRuns().length);
        assertSame(run, n0.getSkiRuns()[0]);

        assertEquals(0, n1.getSkiRuns().length);
    }

    @Test
    void testMultipleEdgesConnectedToCorrectNodes() {
        Node n0 = new Node(0, 1000, 0, 0, true);
        Node n1 = new Node(1, 1100, 1, 1, false);
        Node n2 = new Node(2, 1200, 2, 2, false);

        SkiLift lift1 = new SkiLift(n0, n1, 120, 0, 10, 4);
        SkiLift lift2 = new SkiLift(n0, n2, 180, 1, 15, 6);

        SkiRun run1 = new SkiRun(n1, n0, 90, 0, 3, 0.3, 0.99);
        SkiRun run2 = new SkiRun(n2, n0, 60, 1, 2, 0.4, 0.98);

        SkiResort resort = new SkiResort(
                new Node[]{n0, n1, n2},
                3,
                new SkiLift[]{lift1, lift2},
                2,
                new SkiRun[]{run1, run2},
                2
        );

        assertEquals(2, n0.getSkiLifts().length);

        assertEquals(1, n1.getSkiRuns().length);
        assertEquals(1, n2.getSkiRuns().length);

        assertSame(run1, n1.getSkiRuns()[0]);
        assertSame(run2, n2.getSkiRuns()[0]);
    }

    @Test
    void testNodesWithoutOutgoingEdgesReceiveEmptyArrays() {
        Node n0 = new Node(0, 1000, 0, 0, true);
        Node n1 = new Node(1, 1200, 1, 1, false);

        SkiResort resort = new SkiResort(
                new Node[]{n0, n1},
                2,
                new SkiLift[0],
                0,
                new SkiRun[0],
                0
        );

        assertNotNull(n0.getSkiLifts());
        assertNotNull(n0.getSkiRuns());

        assertNotNull(n1.getSkiLifts());
        assertNotNull(n1.getSkiRuns());

        assertEquals(0, n0.getSkiLifts().length);
        assertEquals(0, n0.getSkiRuns().length);

        assertEquals(0, n1.getSkiLifts().length);
        assertEquals(0, n1.getSkiRuns().length);
    }

    @Test
    void testEdgeOrderRevertedInsideNodeArrays() {
        Node n0 = new Node(0, 1000, 0, 0, true);
        Node n1 = new Node(1, 1200, 1, 1, false);
        Node n2 = new Node(2, 1300, 2, 2, false);

        SkiLift lift1 = new SkiLift(n0, n1, 100, 0, 10, 4);
        SkiLift lift2 = new SkiLift(n0, n2, 200, 1, 20, 8);

        SkiResort resort = new SkiResort(
                new Node[]{n0, n1, n2},
                3,
                new SkiLift[]{lift1, lift2},
                2,
                new SkiRun[0],
                0
        );

        assertSame(lift1, n0.getSkiLifts()[1]);
        assertSame(lift2, n0.getSkiLifts()[0]);
    }

    @Test
    void testConnectResortGraphCanBeCalledAgain() {
        Node n0 = new Node(0, 1000, 0, 0, true);
        Node n1 = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(n0, n1, 120, 0, 10, 4);

        SkiResort resort = new SkiResort(
                new Node[]{n0, n1},
                2,
                new SkiLift[]{lift},
                1,
                new SkiRun[0],
                0
        );

        assertDoesNotThrow(resort::connectResortGraph);

        assertEquals(1, n0.getSkiLifts().length);
        assertSame(lift, n0.getSkiLifts()[0]);
    }

    @Test
    void testMixedRunsAndLiftsOnSingleNode() {
        Node top = new Node(0, 1500, 0, 0, true);
        Node mid = new Node(1, 1200, 1, 1, false);
        Node bottom = new Node(2, 900, 2, 2, false);

        SkiLift lift = new SkiLift(bottom, top, 300, 0, 10, 6);

        SkiRun run1 = new SkiRun(top, mid, 120, 0, 5, 0.3, 0.99);
        SkiRun run2 = new SkiRun(mid, bottom, 90, 1, 3, 0.4, 0.98);

        SkiResort resort = new SkiResort(
                new Node[]{top, mid, bottom},
                3,
                new SkiLift[]{lift},
                1,
                new SkiRun[]{run1, run2},
                2
        );

        assertEquals(1, bottom.getSkiLifts().length);

        assertEquals(1, top.getSkiRuns().length);
        assertEquals(1, mid.getSkiRuns().length);

        assertSame(run1, top.getSkiRuns()[0]);
        assertSame(run2, mid.getSkiRuns()[0]);
    }
}