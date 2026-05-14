package containers;

import org.junit.jupiter.api.Test;
import simulation.Event;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class QueueNodeTest {

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
    void testSingleNodeLinksAreNull() {
        QueueNode node = new QueueNode(e(10, 0, 0), null, null);

        assertNull(node.getNext());
        assertNull(node.getPrev());
    }

    @Test
    void testEmplaceBehindBasicLink() {
        QueueNode n1 = new QueueNode(e(10, 0, 0), null, null);
        QueueNode n2 = new QueueNode(e(10, 1, 0), null, null);

        n1.emplaceBefore(n2.getValue());

        assertNotNull(n1.getNext());
        assertEquals(n2.getValue().getTime(), n1.getNext().getValue().getTime());

        assertEquals(n1, n1.getNext().getPrev());
    }

    @Test
    void testEmplaceBeforeBasicLink() {
        QueueNode n2 = new QueueNode(e(10, 1, 0), null, null);
        QueueNode n1 = new QueueNode(e(10, 0, 0), null, null);

        n2.emplaceBehind(n1.getValue());

        assertNotNull(n2.getPrev());
        assertEquals(n1.getValue().getTime(), n2.getPrev().getValue().getTime());

        assertEquals(n2, n2.getPrev().getNext());
    }

    @Test
    void testChainEmplaceBehindMultipleNodes() {
        QueueNode n1 = new QueueNode(e(9, 0, 0), null, null);
        n1.emplaceBefore(e(9, 1, 0));
        n1.emplaceBefore(e(9, 2, 0));

        QueueNode n2 = n1.getNext();
        QueueNode n3 = n2.getNext();

        assertNotNull(n2);
        assertNotNull(n3);

        assertEquals(e(9, 2, 0).getTime(), n2.getValue().getTime());
        assertEquals(e(9, 1, 0).getTime(), n3.getValue().getTime());

        // back links
        assertEquals(n1, n2.getPrev());
        assertEquals(n2, n3.getPrev());
    }

    @Test
    void testChainEmplaceBeforeMultipleNodes() {
        QueueNode n3 = new QueueNode(e(9, 2, 0), null, null);
        n3.emplaceBehind(e(9, 1, 0));
        n3.emplaceBehind(e(9, 0, 0));

        QueueNode n1 = n3.getPrev().getPrev();
        QueueNode n2 = n3.getPrev();

        assertEquals(e(9, 1, 0).getTime(), n1.getValue().getTime());
        assertEquals(e(9, 0, 0).getTime(), n2.getValue().getTime());
        assertEquals(e(9, 2, 0).getTime(), n3.getValue().getTime());

        // forward links
        assertEquals(n2, n1.getNext());
        assertEquals(n3, n2.getNext());
    }

    @Test
    void testMixedEmplaceBeforeAndBehind() {
        QueueNode middle = new QueueNode(e(12, 0, 0), null, null);

        middle.emplaceBehind(e(11, 0, 0));
        middle.emplaceBefore(e(13, 0, 0));

        QueueNode prev = middle.getPrev();
        QueueNode next = middle.getNext();

        assertNotNull(prev);
        assertNotNull(next);

        assertEquals(e(11, 0, 0).getTime(), prev.getValue().getTime());
        assertEquals(e(13, 0, 0).getTime(), next.getValue().getTime());

        assertEquals(middle, prev.getNext());
        assertEquals(middle, next.getPrev());
    }

}