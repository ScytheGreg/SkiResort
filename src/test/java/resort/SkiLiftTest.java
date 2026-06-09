package resort;

import athlete.Athlete;
import athlete.AthleteParameters;
import athlete.LocalAthlete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import simulation.Event;
import containers.Queue;

import java.time.LocalTime;

public class SkiLiftTest {

    private static SkiLift lift;
    private Athlete[] athletes = new Athlete[5];
    private LocalTime time = LocalTime.of(10, 0);

    private Athlete simplifiedAthlete(int id) {
        AthleteParameters p = new AthleteParameters(id, 1, 1, 1,
                1, 1, 1, null, null, null);
        return new LocalAthlete(p);
    }

    @BeforeEach
    void setUp() {
        lift = new SkiLift(null, null, 1, 1, 22, 3);
        for (int i = 0; i < athletes.length; ++i) {
            athletes[i] = simplifiedAthlete(i);
        }
    }

    private void pickUpAndRide() {
        Queue liftRideEvents = lift.pickUpPassengers(time);
        while (!liftRideEvents.isEmpty()) {
            liftRideEvents.takeFront().performAction(0);
        }
    }

    @Test
    void shouldPickUpMaximumNumbersOfPassengers() {
        for (int i = 0; i < 4; ++i) {
            athletes[i].queueUp(lift, time);
        }
        pickUpAndRide();
        assertEquals(3, lift.getServedAthletesCnt());
    }

    @Test
    void shouldReduceQueueAfterPickup() {
        athletes[0].queueUp(lift, time);
        athletes[1].queueUp(lift, time);

        pickUpAndRide();

        assertEquals(1, lift.pickUpPassengers(time).size()); // Every pickup returns next pickup
        // No more athletes should be there
    }

    @Test
    void shouldCalculateMaxQueueLength() {
        for (int i = 0; i < 4; ++i) {
            athletes[i].queueUp(lift, time);
        }
        pickUpAndRide();
        athletes[4].queueUp(lift, time);

        assertEquals(4, lift.getMaxQueueLength());
    }
}
