package simulation;

import athlete.Athlete;
import containers.Queue;

import java.time.LocalTime;

public class MakeDecision extends AthleteEvent {
    public MakeDecision(LocalTime time, Athlete athlete){super(time, athlete);}
    public Queue performAction(){
        return new Event[0];
    }
}
