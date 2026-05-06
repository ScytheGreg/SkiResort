package simulation;

import java.time.LocalTime;
import containers.PriorityQueue;
import containers.*;

public class Simulation {
    static final private int endTime = LocalTime.of(15, 0).toSecondOfDay();
    private final PriorityQueue events;

    public Simulation(PriorityQueue events){
        this.events= events;
    }

    public void simulate() throws emptyContainerException {
        while(!events.isEmpty() && events.getFront().getTime() < endTime){
            Queue newEvents = events.takeFront().performAction();
            while(!newEvents.isEmpty())
                events.push(newEvents.takeFront());
        }
    }
}
