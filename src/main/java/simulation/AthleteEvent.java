package simulation;

import athlete.Athlete;

import java.time.LocalTime;

abstract public class AthleteEvent extends Event {
    private Athlete athlete;

    public AthleteEvent(LocalTime time, Athlete athlete) {
        super(time);
        this.athlete = athlete;
    }

    public Athlete getAthlete() {
        return athlete;
    }
}
