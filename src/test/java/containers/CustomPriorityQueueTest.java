package containers;

import org.junit.jupiter.api.Test;
import simulation.Event;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomPriorityQueueTest {

    static class TestEvent extends Event {
        public TestEvent(LocalTime time) {
            super(time);
        }
        public Queue performAction(int seed){return new Queue();}
    }

    private TestEvent e(int h, int m, int s) {
        return new TestEvent(LocalTime.of(h, m, s));
    }

    @Test
    void testEmptyPriorityQueue() {
        CustomPriorityQueue q = new CustomPriorityQueue();

        assertTrue(q.isEmpty());

        assertThrows(emptyContainerException.class, q::getFront);
        assertThrows(emptyContainerException.class, q::takeFront);
    }

    @Test
    void testSingleElementPush() throws Exception {
        CustomPriorityQueue q = new CustomPriorityQueue();

        TestEvent e1 = e(10, 0, 0);

        q.push(e1);

        assertFalse(q.isEmpty());
        assertEquals(e1, q.getFront());
    }

    @Test
    void testAlreadySortedInsertion() throws Exception {
        CustomPriorityQueue q = new CustomPriorityQueue();

        TestEvent e1 = e(9, 0, 0);
        TestEvent e2 = e(9, 5, 0);
        TestEvent e3 = e(9, 10, 0);

        q.push(e1);
        q.push(e2);
        q.push(e3);

        assertEquals(e1, q.takeFront());
        assertEquals(e2, q.takeFront());
        assertEquals(e3, q.takeFront());

        assertTrue(q.isEmpty());
    }

    @Test
    void testReverseOrderInsertion() throws Exception {
        CustomPriorityQueue q = new CustomPriorityQueue();

        TestEvent e1 = e(9, 10, 0);
        TestEvent e2 = e(9, 5, 0);
        TestEvent e3 = e(9, 0, 0);

        q.push(e1);
        q.push(e2);
        q.push(e3);

        assertEquals(e3, q.takeFront());
        assertEquals(e2, q.takeFront());
        assertEquals(e1, q.takeFront());

        assertTrue(q.isEmpty());
    }

    @Test
    void testRandomInsertionOrder() throws Exception {
        CustomPriorityQueue q = new CustomPriorityQueue();

        TestEvent e1 = e(12, 30, 0);
        TestEvent e2 = e(8, 15, 0);
        TestEvent e3 = e(15, 45, 0);
        TestEvent e4 = e(10, 0, 0);

        q.push(e1);
        q.push(e2);
        q.push(e3);
        q.push(e4);

        assertEquals(e2, q.takeFront());
        assertEquals(e4, q.takeFront());
        assertEquals(e1, q.takeFront());
        assertEquals(e3, q.takeFront());

        assertTrue(q.isEmpty());
    }

    @Test
    void testEqualTimesPreserveInsertionOrder() throws Exception {
        CustomPriorityQueue q = new CustomPriorityQueue();

        TestEvent e1 = e(9, 0, 0);
        TestEvent e2 = e(9, 0, 0);
        TestEvent e3 = e(9, 0, 0);

        q.push(e1);
        q.push(e2);
        q.push(e3);

        // Requirement:
        // later inserted events with same time
        // should be returned later

        assertEquals(e1, q.takeFront());
        assertEquals(e2, q.takeFront());
        assertEquals(e3, q.takeFront());
    }

    @Test
    void testMixedEqualAndDifferentTimes() throws Exception {
        CustomPriorityQueue q = new CustomPriorityQueue();

        TestEvent e1 = e(9, 0, 0);
        TestEvent e2 = e(9, 5, 0);
        TestEvent e3 = e(9, 0, 0);
        TestEvent e4 = e(8, 59, 59);

        q.push(e1);
        q.push(e2);
        q.push(e3);
        q.push(e4);

        assertEquals(e4, q.takeFront());
        assertEquals(e1, q.takeFront());
        assertEquals(e3, q.takeFront());
        assertEquals(e2, q.takeFront());
    }

    @Test
    void testNullPushIgnored() {
        CustomPriorityQueue q = new CustomPriorityQueue();

        q.push(null);

        assertTrue(q.isEmpty());
    }

    @Test
    void testPopAfterPriorityInsertions() throws Exception {
        CustomPriorityQueue q = new CustomPriorityQueue();

        q.push(e(10, 0, 0));
        q.push(e(9, 0, 0));
        q.push(e(11, 0, 0));

        q.pop();

        assertEquals(LocalTime.of(10, 0, 0),
                q.getFront().getTime());

        q.pop();

        assertEquals(LocalTime.of(11, 0, 0),
                q.getFront().getTime());
    }

    @Test
    void testLargeInsertionSequence() throws Exception {
        CustomPriorityQueue q = new CustomPriorityQueue();

        for (int i = 59; i >= 0; --i) {
            q.push(e(9, i, 0));
        }

        for (int i = 0; i < 60; ++i) {
            assertEquals(
                    LocalTime.of(9, i, 0),
                    q.takeFront().getTime()
            );
        }

        assertTrue(q.isEmpty());
    }
}