package simulation;

import containers.Queue;
import resort.SkiLift;

import java.time.LocalTime;

public class PickUpPassengers extends EdgeEvent{
    SkiLift lift;
    public PickUpPassengers(LocalTime time, SkiLift lift){
        super(time, lift); this.lift = lift;
    }

    @Override
    public Queue performAction() {
        return lift.pickUpPassengers(getLocalTime());
    }
}
