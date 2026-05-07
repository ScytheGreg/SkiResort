package simulation;

import java.time.LocalTime;

import containers.PriorityQueue;
import containers.*;
import read.Input;
import resort.*;

public class Simulation {
    static final private LocalTime START_TIME = LocalTime.of(9, 0);
    static final private LocalTime END_TIME = LocalTime.of(16, 0);
    private final PriorityQueue events;

    public Simulation(PriorityQueue events, SkiResort resort) {
        this.events = events;
        loadPickUpEvents(resort.getSkiLifts());
    }

    public void loadPickUpEvents(SkiLift[] lifts) {
        for (SkiLift lift : lifts) {
            events.push(new PickUpPassengers(START_TIME, lift));
        }
    }

    public void simulate(int seed, SkiResort resort) throws emptyContainerException {
        while (!events.isEmpty() && events.getFront().getTime().isBefore(END_TIME)) {
            Queue newEvents = events.takeFront().performAction(seed);
            while (!newEvents.isEmpty())
                events.push(newEvents.takeFront());
        }
        resort.log();
    }
}
