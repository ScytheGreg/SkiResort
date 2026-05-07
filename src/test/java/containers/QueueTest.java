package containers;

import org.junit.jupiter.api.Test;
import simulation.Event;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    // prosty event testowy
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
    void testEmptyQueue() {
        Queue q = new Queue();

        assertTrue(q.isEmpty());
        assertThrows(emptyContainerException.class, q::getFront);
        assertThrows(emptyContainerException.class, q::takeFront);
    }

    @Test
    void testPushOneElement() throws Exception {
        Queue q = new Queue();

        TestEvent e1 = e(10, 0, 0);
        q.push(e1);

        assertFalse(q.isEmpty());
        assertEquals(e1, q.getFront());
        assertEquals(e1, q.takeFront());
        assertTrue(q.isEmpty());
    }

    @Test
    void testFIFOOrder() throws Exception {
        Queue q = new Queue();

        TestEvent e1 = e(9, 0, 0);
        TestEvent e2 = e(9, 1, 0);
        TestEvent e3 = e(9, 2, 0);

        q.push(e1);
        q.push(e2);
        q.push(e3);

        assertEquals(e1, q.takeFront());
        assertEquals(e2, q.takeFront());
        assertEquals(e3, q.takeFront());

        assertTrue(q.isEmpty());
    }

    @Test
    void testPopRemovesElements() throws Exception {
        Queue q = new Queue();

        TestEvent e1 = e(12, 0, 0);
        TestEvent e2 = e(12, 0, 1);

        q.push(e1);
        q.push(e2);

        q.pop();
        assertEquals(e2, q.getFront());

        q.pop();
        assertTrue(q.isEmpty());
    }

    @Test
    void testTakeFrontDoesNotLeaveCorruption() throws Exception {
        Queue q = new Queue();

        q.push(e(8, 0, 0));
        q.push(e(8, 0, 1));

        q.takeFront();
        assertEquals(LocalTime.of(8, 0, 1), q.getFront().getTime());

        q.takeFront();
        assertTrue(q.isEmpty());
    }

    @Test
    void testSingleElementQueueBehavior() throws Exception {
        Queue q = new Queue();

        q.push(e(7, 0, 0));

        assertFalse(q.isEmpty());
        assertTrue(q.isSingular());

        q.pop();
        assertTrue(q.isEmpty());
    }

    @Test
    void testNullPushIgnoredOrSafe() {
        Queue q = new Queue();

        q.push(null);

        assertTrue(q.isEmpty());
    }
}