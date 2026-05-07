package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.Edge;

import java.time.LocalTime;

public class QueueUp extends AthleteEvent{

    public QueueUp(LocalTime time, Athlete athlete, Edge edge){super(time, athlete, edge);}

    public Queue performAction(int seed){
        getAthlete().queueUp(getEdge());
        return new Queue();
    }

    @Override
    public String toString(){
        return super.toString() + " has queued up to lift nr " + getEdge().getId();
    }
}
