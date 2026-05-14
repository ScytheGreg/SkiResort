package resort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testConstructorAndBasicGetters() {
        Node node = new Node(7, 1200, 15, 30, true);

        assertEquals(7, node.getId());
        assertTrue(node.isCommunicated());
    }

    @Test
    void testNonCommunicatedNode() {
        Node node = new Node(3, 900, 5, 8, false);

        assertFalse(node.isCommunicated());
    }

    @Test
    void testSetAndGetSkiRuns() {
        Node node = new Node(0, 1000, 0, 0, true);

        SkiRun[] runs = new SkiRun[2];

        node.setSkiRuns(runs);

        assertSame(runs, node.getSkiRuns());
        assertEquals(2, node.getSkiRuns().length);
    }

    @Test
    void testSetAndGetSkiLifts() {
        Node node = new Node(0, 1000, 0, 0, true);

        SkiLift[] lifts = new SkiLift[3];

        node.setSkiLifts(lifts);

        assertSame(lifts, node.getSkiLifts());
        assertEquals(3, node.getSkiLifts().length);
    }

    @Test
    void testGetLiftNeighboursWithoutLifts() {
        Node node = new Node(1, 1000, 0, 0, true);

        node.setSkiLifts(new SkiLift[0]);

        Node[] neighbours = node.getLiftNeighbours();

        assertEquals(1, neighbours.length);
        assertSame(node, neighbours[0]);
    }

    @Test
    void testGetLiftNeighboursSingleLift() {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                start,
                end,
                120,
                0,
                10,
                4
        );

        start.setSkiLifts(new SkiLift[]{lift});

        Node[] neighbours = start.getLiftNeighbours();

        assertEquals(2, neighbours.length);

        assertSame(start, neighbours[0]);
        assertSame(end, neighbours[1]);
    }

    @Test
    void testGetLiftNeighboursMultipleLifts() {
        Node start = new Node(0, 1000, 0, 0, true);

        Node end1 = new Node(1, 1100, 1, 1, false);
        Node end2 = new Node(2, 1200, 2, 2, false);
        Node end3 = new Node(3, 1300, 3, 3, false);

        SkiLift l1 = new SkiLift(start, end1, 120, 0, 10, 4);
        SkiLift l2 = new SkiLift(start, end2, 180, 1, 15, 6);
        SkiLift l3 = new SkiLift(start, end3, 240, 2, 20, 8);

        start.setSkiLifts(new SkiLift[]{l1, l2, l3});

        Node[] neighbours = start.getLiftNeighbours();

        assertEquals(4, neighbours.length);

        assertSame(start, neighbours[0]);
        assertSame(end1, neighbours[1]);
        assertSame(end2, neighbours[2]);
        assertSame(end3, neighbours[3]);
    }

    @Test
    void testGetLiftNeighboursPreservesOrder() {
        Node start = new Node(0, 1000, 0, 0, true);

        Node a = new Node(1, 1100, 0, 0, false);
        Node b = new Node(2, 1200, 0, 0, false);

        SkiLift l1 = new SkiLift(start, a, 60, 0, 10, 2);
        SkiLift l2 = new SkiLift(start, b, 60, 1, 10, 2);

        start.setSkiLifts(new SkiLift[]{l1, l2});

        Node[] neighbours = start.getLiftNeighbours();

        assertSame(a, neighbours[1]);
        assertSame(b, neighbours[2]);
    }

    @Test
    void testNullArraysInitially() {
        Node node = new Node(0, 1000, 0, 0, true);

        assertNull(node.getSkiRuns());
        assertNull(node.getSkiLifts());
    }

    @Test
    void testSetAndGetSkiRunsWithActualObjects() {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 900, 1, 1, false);

        SkiRun run = new SkiRun(
                start,
                end,
                90,
                0,
                5,
                0.3,
                0.99
        );

        start.setSkiRuns(new SkiRun[]{run});

        assertEquals(1, start.getSkiRuns().length);
        assertSame(run, start.getSkiRuns()[0]);
    }
}