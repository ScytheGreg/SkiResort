package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.Edge;

import java.time.LocalTime;

public class StartARide extends AthleteEvent{
    public StartARide(LocalTime time, Athlete athlete, Edge edge){super(time, athlete, edge);}

    public Queue performAction(int seed) {
        Queue result = super.performAction(seed);
        getEdge().takeARide();
        getAthlete().move(getEdge());
        result.push(new EndARide(getTime().plusSeconds(getEdge().getRideTime()), getAthlete(), getEdge()));
        return result;
    }

    @Override
    public String toString(){
        return super.toString() + " has begun ride on " + getEdge().getName() + " nr " + getEdge().getId();
    }
}
