package simulation;

import athlete.Athlete;

import java.time.LocalTime;

public class goUpWithLift extends AthleteEvent{
    public goUpWithLift(LocalTime time, Athlete athlete){super(time, athlete);}

    public Event[] performAction() {
        return new Event[0];
    }
}
