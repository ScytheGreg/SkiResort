package resort;

import athlete.Athlete;
import containers.Queue;
import containers.emptyContainerException;
import org.junit.jupiter.api.Test;
import simulation.Event;
import simulation.GoUpWithLift;
import simulation.PickUpPassengers;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class SkiLiftTest {

    @Test
    void testConstructorAndGetters() {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                start,
                end,
                180,
                7,
                15,
                6
        );

        assertEquals(15, lift.getFrequency());
        assertEquals(6, lift.getCapacity());
    }

    @Test
    void testPickUpPassengersFromEmptyQueue() throws Exception {
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

        LocalTime now = LocalTime.of(9, 0);

        Queue result = lift.pickUpPassengers(now);

        assertFalse(result.isEmpty());

        Event event = result.takeFront();

        assertInstanceOf(PickUpPassengers.class, event);
        assertEquals(now.plusSeconds(10), event.getTime());

        assertTrue(result.isEmpty());
    }

    @Test
    void testAddWaitingAddsPassenger() throws Exception {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                start,
                end,
                120,
                0,
                10,
                2
        );

        Athlete athlete = null;

        lift.addWaiting(athlete);

        Queue picked = lift.pickUpPassengers(LocalTime.of(9, 0));

        Event first = picked.takeFront();

        assertInstanceOf(GoUpWithLift.class, first);
    }

    @Test
    void testPickUpPassengersRespectsCapacity() throws Exception {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                start,
                end,
                120,
                0,
                10,
                2
        );

        lift.addWaiting(null);
        lift.addWaiting(null);
        lift.addWaiting(null);

        Queue result = lift.pickUpPassengers(LocalTime.of(9, 0));

        Event e1 = result.takeFront();
        Event e2 = result.takeFront();
        Event e3 = result.takeFront();

        assertInstanceOf(GoUpWithLift.class, e1);
        assertInstanceOf(GoUpWithLift.class, e2);

        // third should be recurring pickup event
        assertInstanceOf(PickUpPassengers.class, e3);

        assertTrue(result.isEmpty());
    }

    @Test
    void testPickedPassengersReceiveCurrentTime() throws Exception {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                start,
                end,
                120,
                0,
                20,
                3
        );

        lift.addWaiting(null);

        LocalTime pickupTime = LocalTime.of(10, 15, 30);

        Queue result = lift.pickUpPassengers(pickupTime);

        Event passengerEvent = result.takeFront();

        assertEquals(pickupTime, passengerEvent.getTime());
    }

    @Test
    void testRecurringPickupEventHasCorrectTime() throws Exception {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                start,
                end,
                300,
                0,
                12,
                5
        );

        LocalTime now = LocalTime.of(11, 0, 0);

        Queue result = lift.pickUpPassengers(now);

        Event recurring = result.takeFront();

        assertEquals(
                now.plusSeconds(12),
                recurring.getTime()
        );
    }

    @Test
    void testMultiplePickupCycles() throws Exception {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                start,
                end,
                120,
                0,
                10,
                1
        );

        lift.addWaiting(null);
        lift.addWaiting(null);

        Queue firstCycle = lift.pickUpPassengers(LocalTime.of(9, 0));

        Event firstPassenger = firstCycle.takeFront();
        Event recurring1 = firstCycle.takeFront();

        assertInstanceOf(GoUpWithLift.class, firstPassenger);
        assertInstanceOf(PickUpPassengers.class, recurring1);

        Queue secondCycle = lift.pickUpPassengers(LocalTime.of(9, 0, 10));

        Event secondPassenger = secondCycle.takeFront();

        assertInstanceOf(GoUpWithLift.class, secondPassenger);
    }

    @Test
    void testNoExceptionWhenCapacityGreaterThanWaitingCount() {
        Node start = new Node(0, 1000, 0, 0, true);
        Node end = new Node(1, 1200, 1, 1, false);

        SkiLift lift = new SkiLift(
                start,
                end,
                120,
                0,
                10,
                10
        );

        lift.addWaiting(null);

        assertDoesNotThrow(() ->
                lift.pickUpPassengers(LocalTime.of(9, 0))
        );
    }
}