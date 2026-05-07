package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.Edge;
import resort.SkiLift;

import java.time.LocalTime;

public class EndARide extends AthleteEvent{
    public EndARide(LocalTime time, Athlete athlete, Edge edge){super(time, athlete, edge);}

    public Queue performAction(int seed){
        Queue result = super.performAction(seed);
        result.push(getAthlete().makeDecision(seed, getTime()));
        return result;
    }
    @Override
    public String toString(){
        return super.toString() + " has end ride on " + getEdge().getName() + " nr " + getEdge().getId();
    }
}
