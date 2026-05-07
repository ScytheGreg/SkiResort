package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.SkiLift;

import java.time.LocalTime;

public class GoUpWithLift extends AthleteEvent{
    private final SkiLift lift;
    public GoUpWithLift(LocalTime time, Athlete athlete, SkiLift lift){super(time, athlete);this.lift = lift;}

    public Queue performAction(int seed) {
        lift.takeARide();
        getAthlete().move(lift);
        Queue result = new Queue();
        result.push(new MakeDecision(getTime().plusSeconds(lift.getRideTime()), getAthlete()));
        return result;
    }

    @Override
    public String toString(){
        return super.toString() + " is going up with lift.";
    }
}
