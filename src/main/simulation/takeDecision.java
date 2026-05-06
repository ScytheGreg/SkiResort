package simulation;

import athlete.Athlete;

import java.time.LocalTime;

public class takeDecision extends AthleteEvent {
    public takeDecision(LocalTime time, Athlete athlete){super(time, athlete);}
    public Event[] performAction(){
        return new Event[0];
    }
}
