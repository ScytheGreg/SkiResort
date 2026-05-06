package simulation;

import athlete.Athlete;
import containers.Queue;

import java.time.LocalTime;

public class GoUpWithLift extends AthleteEvent{
    public GoUpWithLift(LocalTime time, Athlete athlete){super(time, athlete);}

    public Queue performAction() {
        return new Queue();
    }
}
