package athlete;

import org.junit.jupiter.api.Test;
import resort.Edge;
import resort.Node;
import resort.SkiLift;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AthleteTest {

    private Node node(int id) {
        Node n = mock(Node.class);
        when(n.getId()).thenReturn(id);
        return n;
    }

    @Test
    void constructorShouldInitializeFieldsCorrectly() {
        Node start = node(1);

        Athlete athlete = new Athlete(
                0,
                5,
                0.3,
                true,
                0.7,
                0.3,
                start,
                LocalTime.of(9, 0)
        );

        assertEquals(start, athlete.getCurrentNode());
        assertEquals(LocalTime.of(9, 0), athlete.getStartTime());
        assertEquals(0.3, athlete.getImpulsivity());
    }

    @Test
    void moveShouldChangeCurrentNode() {
        Node a = node(1);
        Node b = node(2);

        Athlete athlete = new Athlete(
                0, 5, 0.3, false, 0.5, 0.5,
                a,
                LocalTime.of(9, 0)
        );

        Edge edge = mock(Edge.class);
        when(edge.getBeginning()).thenReturn(a);
        when(edge.getEnd()).thenReturn(b);

        athlete.move(edge);

        assertEquals(b, athlete.getCurrentNode());
    }

    @Test
    void moveShouldFailIfWrongStartNode() {
        Node a = node(1);
        Node b = node(2);
        Node wrong = node(999);

        Athlete athlete = new Athlete(
                0, 5, 0.3, false, 0.5, 0.5,
                a,
                LocalTime.of(9, 0)
        );

        Edge edge = mock(Edge.class);
        when(edge.getBeginning()).thenReturn(wrong);
        when(edge.getEnd()).thenReturn(b);

        assertThrows(AssertionError.class, () -> athlete.move(edge));
    }

    @Test
    void queueUpShouldCallLift() {
        Athlete athlete = new Athlete(
                0, 5, 0.3, false, 0.5, 0.5,
                node(1),
                LocalTime.of(9, 0)
        );

        SkiLift lift = mock(SkiLift.class);

        athlete.queueUp(lift);

        verify(lift, times(1)).addWaiting(athlete);
    }

    @Test
    void startTimeShouldBeStoredCorrectly() {
        LocalTime time = LocalTime.of(10, 15, 30);

        Athlete athlete = new Athlete(
                1, 5, 0.3, false, 0.5, 0.5,
                node(1),
                time
        );

        assertEquals(time, athlete.getStartTime());
    }

    @Test
    void impulsivityShouldBeStored() {
        Athlete athlete = new Athlete(
                1, 5, 0.9, false, 0.5, 0.5,
                node(1),
                LocalTime.of(9, 0)
        );

        assertEquals(0.9, athlete.getImpulsivity());
    }

    @Test
    void currentNodeShouldUpdateAfterMultipleMoves() {
        Node a = node(1);
        Node b = node(2);
        Node c = node(3);

        Athlete athlete = new Athlete(
                0, 5, 0.3, false, 0.5, 0.5,
                a,
                LocalTime.of(9, 0)
        );

        Edge e1 = mock(Edge.class);
        when(e1.getBeginning()).thenReturn(a);
        when(e1.getEnd()).thenReturn(b);

        Edge e2 = mock(Edge.class);
        when(e2.getBeginning()).thenReturn(b);
        when(e2.getEnd()).thenReturn(c);

        athlete.move(e1);
        athlete.move(e2);

        assertEquals(c, athlete.getCurrentNode());
    }
}