package simulation;

import containers.Queue;
import resort.SkiLift;

import java.time.LocalTime;

public class PickUpPassengers extends Event{
    SkiLift lift;
    public PickUpPassengers(LocalTime time, SkiLift lift){
        super(time); this.lift = lift;
    }

    @Override
    public Queue performAction(int seed) {
        return lift.pickUpPassengers(getTime());
    }
}
