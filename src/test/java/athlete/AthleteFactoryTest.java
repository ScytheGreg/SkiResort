package athlete;

import org.junit.jupiter.api.Test;
import resort.Node;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AthleteFactoryTest {

    private Node[] createNodes() {
        Node[] nodes = new Node[3];
        nodes[0] = new Node(0, 1000, 0, 0, true);
        nodes[1] = new Node(1, 1200, 1, 1, false);
        nodes[2] = new Node(2, 1400, 2, 2, false);
        return nodes;
    }

    @Test
    void testCreateAthleteWithoutTrackingAndNoDelay() {
        Node[] nodes = createNodes();

        String data =
                "1 " +          // number of athletes
                        "5 0.7 " +      // advanceLevel, impulsivity
                        "0.8 0.3 " +    // levelMatchLure, runSmoothnessLure
                        "1 " +          // start node
                        "09:00:00";     // start time

        Athlete athlete = AthleteFactory.create(data, 0, 10, nodes);

        assertEquals(10, athlete.getId());
        assertEquals(5, athlete.getAdvanceLevel());
        assertEquals(0.7, athlete.getImpulsivity(), 1e-9);
        assertFalse(athlete.isTracked());
        assertEquals(nodes[1], athlete.getCurrentNode());
        assertEquals(LocalTime.of(9, 0), athlete.getStartTime());
    }

    @Test
    void testCreateAthleteWithTracking() {
        Node[] nodes = createNodes();

        String data =
                "1 " +
                        "3 0.2 " +
                        "s " +          // tracked flag
                        "0.9 0.1 " +
                        "2 " +
                        "10:15:00";

        Athlete athlete = AthleteFactory.create(data, 0, 1, nodes);

        assertTrue(athlete.isTracked());
        assertEquals(nodes[2], athlete.getCurrentNode());
        assertEquals(LocalTime.of(10, 15), athlete.getStartTime());
    }

    @Test
    void testCreateAthleteWithDelay() {
        Node[] nodes = createNodes();

        String data =
                "1 " +
                        "4 0.5 " +
                        "0.7 0.2 " +
                        "0 " +
                        "09:00:00 " +
                        "30"; // delay

        Athlete athlete = AthleteFactory.create(data, 2, 5, nodes);

        // groupId = 2, delay = 30 -> 60 seconds shift
        assertEquals(LocalTime.of(9, 1, 0), athlete.getStartTime());
    }

    @Test
    void testMultipleAthletesGroupOffset() {
        Node[] nodes = createNodes();

        String data =
                "1 " +
                        "6 0.4 " +
                        "0.6 0.6 " +
                        "1 " +
                        "09:00:00 " +
                        "10";

        Athlete a0 = AthleteFactory.create(data, 0, 0, nodes);
        Athlete a1 = AthleteFactory.create(data, 1, 1, nodes);

        assertEquals(LocalTime.of(9, 0, 0), a0.getStartTime());
        assertEquals(LocalTime.of(9, 0, 10), a1.getStartTime());
    }
}