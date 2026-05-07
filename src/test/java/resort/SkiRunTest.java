package resort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkiRunTest {

    @Test
    void testConstructorAndGetters() {
        Node start = new Node(0, 1200, 0, 0, true);
        Node end = new Node(1, 900, 1, 1, false);

        SkiRun run = new SkiRun(
                start,
                end,
                120,
                5,
                7,
                0.3,
                0.99
        );

        assertEquals(7, run.getAdvanceLevel());
    }

    @Test
    void testSkierRideIncrementsCounterEffect() {
        Node start = new Node(0, 1200, 0, 0, true);
        Node end = new Node(1, 900, 1, 1, false);

        SkiRun run = new SkiRun(
                start,
                end,
                120,
                0,
                5,
                0.3,
                0.9
        );

        double initial = run.smoothnessCalculate();

        run.takeARide();
        run.takeARide();

        double after = run.smoothnessCalculate();

        assertTrue(after < initial);
    }

    @Test
    void testSmoothnessInitialValue() {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 900, 1, 1, false);

        SkiRun run = new SkiRun(
                start,
                end,
                90,
                0,
                3,
                0.5,
                1.0
        );

        assertEquals(1.0, run.smoothnessCalculate(), 1e-9);
    }

    @Test
    void testSmoothnessDecreasesWithToughnessLessThanOne() {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 900, 1, 1, false);

        SkiRun run = new SkiRun(
                start,
                end,
                90,
                0,
                3,
                0.2,
                0.5
        );

        double s0 = run.smoothnessCalculate();

        run.takeARide();
        double s1 = run.smoothnessCalculate();

        run.takeARide();
        double s2 = run.smoothnessCalculate();

        assertTrue(s0 > s1);
        assertTrue(s1 > s2);
    }

    @Test
    void testSmoothnessDoesNotGoBelowBaseLure() {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 900, 1, 1, false);

        SkiRun run = new SkiRun(
                start,
                end,
                90,
                0,
                3,
                0.3,
                0.0
        );

        for (int i = 0; i < 10; i++) {
            run.takeARide();
        }

        assertEquals(0.3, run.smoothnessCalculate(), 1e-9);
    }

    @Test
    void testSmoothnessFormulaConsistency() {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 900, 1, 1, false);

        SkiRun run = new SkiRun(
                start,
                end,
                100,
                0,
                4,
                0.25,
                0.8
        );

        run.takeARide();
        run.takeARide();
        run.takeARide();

        double expected = 0.25 + (1 - 0.25) * Math.pow(0.8, 3);

        assertEquals(expected, run.smoothnessCalculate(), 1e-9);
    }

    @Test
    void testMultipleRunsIndependentState() {
        Node a = new Node(0, 1200, 0, 0, true);
        Node b = new Node(1, 900, 1, 1, false);

        SkiRun r1 = new SkiRun(a, b, 100, 0, 5, 0.3, 0.9);
        SkiRun r2 = new SkiRun(a, b, 100, 1, 5, 0.3, 0.9);

        r1.takeARide();
        r1.takeARide();

        assertTrue(r1.smoothnessCalculate() < r2.smoothnessCalculate());
    }
}